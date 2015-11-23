package com.test.BackendService.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.BackendService.Utils.ResponseBean;
import com.test.BackendService.Utils.SessionIdGenerator;
import com.test.BackendService.dtos.OrderInput;
import com.test.BackendService.dtos.PaymentDTO;
import com.test.BackendService.dtos.Product;
import com.test.Enums.PgCode;
import com.test.Enums.Status;
import com.test.payment.pg.PgResponseEntity;

@RestController
@RequestMapping({ "/api/v1", "" })
public class OrderController {

	@Autowired
	PaymentController paymentController;

	@Autowired
	ResponseBean responseBean;

	@Autowired
	PgResponseEntity pgResponseEntity;

	@Autowired
	SessionIdGenerator sessionIdGenerator;

	final static Logger logger = Logger.getLogger(OrderController.class
			.getName());

	private final Map<String, String> concurrentCheckMap = new ConcurrentHashMap<String, String>();

	/*
	 * Sample controller for test purpose.
	 */
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ResponseBean displayMessage() throws Exception {
		logger.info("Getting hit for displayMessage");
		OrderInput orderInput = new OrderInput();
		orderInput.setOrderId("12345");
		List<Product> productList = new ArrayList<Product>();
		Product pr = new Product();
		pr.setProductId("123456");
		pr.setQty(5);
		productList.add(pr);
		orderInput.setProductList(productList);

		JSONObject obj = new JSONObject();
		/*
		 * obj.put("orderId","d47rWnbZ7q"); obj.put("productList",productList);
		 */
		obj.put("orderInput", orderInput);
		return new ResponseBean(HttpStatus.OK, obj);
	}

	/*
	 * API to create Payment and update Product table with the correct quantity
	 */
	@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseBean updateOrder(@RequestBody OrderInput orderInput)
			throws Exception {
		logger.info("Getting hit for updateOrder");
		logger.debug("Getting hit for updateOrder");
		String productName = null;
		Integer availableQty = null;
		String orderNo=orderInput.getOrderNo();;
		Map<String, Integer> productResult = new HashMap<String, Integer>();
		boolean allItemsReady = true;
		/*
		 * In Order to avoid concurrent access with the same order no
		 */
		if (!concurrentCheckMap.containsKey(orderNo)) {
			concurrentCheckMap.put(orderNo,
					Status.INPROGRESS.toString());
		} else {
			return new ResponseBean(HttpStatus.BAD_REQUEST,
					"Concurrent request found.Cann't proceed");
		}

		logger.debug("OrderId requested is: " + orderInput.getOrderId());
		logger.info("OrderId requested is: " + orderInput.getOrderId());

		List<Product> productList = orderInput.getProductList();
		logger.info("No of products in the list is : " + productList.size());
		logger.debug("No of products in the list is : " + productList.size());

		ParseObject Product = new ParseObject("Product");
		List<ParseObject> products = new ArrayList<ParseObject>();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
		
		for (Product product : productList) {
			logger.info("ProductId requested is: " + product.getProductId());
			logger.info("No of Qty ordered is: : " + product.getQty());
			query.whereEqualTo("objectId", product.getProductId());
			Integer newQty = 0;
			try {				
				List<ParseObject> productDetails = query.find();
				if (productDetails != null) {
					Product = productDetails.get(0);
					productName = Product.get("productName").toString();
					availableQty = Integer.parseInt(Product.get("availableQty")
							.toString());

					logger.info("Product retrieved is: " + productName);
					logger.info("Available Qty for the Product retrieved is: "
							+ availableQty);

					if (availableQty > 0 && availableQty >= product.getQty()) {
						newQty = availableQty - product.getQty();
						Product.put("availableQty", newQty.toString());
						products.add(Product);
					} else {
						productResult.put(product.getProductId(), availableQty);
						allItemsReady = false;
					}
				}

			} catch (ParseException e) {
				logger.error(e);
			}
		}
		if (allItemsReady) {
			/** Initiate Payment for the items: */
			PaymentDTO paymentDTO = new PaymentDTO();
			/** For Testing purpose amount is set to 1. */
			/* paymentDTO.setAmount(orderInput.getTotalAmt()); */
			paymentDTO.setAmount(1);
			paymentDTO.setBookingNo(orderNo); // Setting BookingNo as OrderNo
			paymentDTO.setCheckSum("testCheckSum");
			paymentDTO.setComment("BookingForEcommerce");
			paymentDTO.setCurrency("INR");
			paymentDTO.setCustomerNo(orderInput.getCustomerNo());
			/* paymentDTO.setPgCode(getPgCode(orderInput.getPgCode())); */
			paymentDTO.setPgCode(getPgCode(PgCode.CITRUSPAY.toString()));
			paymentDTO.setStatus(1);
			responseBean = paymentController.MakePayment(paymentDTO);
			/*
			 * Creating a transaction id which will be unique for each
			 * transaction.
			 */
			String trasactionId = sessionIdGenerator
					.generateSessionId(orderNo);
			pgResponseEntity = (PgResponseEntity) responseBean.getStatus();
			if (pgResponseEntity != null) {
				if (Status.SUCCESSFUL.toString().equalsIgnoreCase(
						pgResponseEntity.getStatus())) {

					pgResponseEntity.setStatus(Status.SUCCESS.toString());
					pgResponseEntity.setPgTransactionId(trasactionId);
					int result = createPayment(pgResponseEntity);
					for (ParseObject pObj : products) {
						pObj.save();
					}
					updateOrder(orderNo,Status.SUCCESS.toString());
					if (result == 0) {
						pgResponseEntity
								.setErrorMessage("Failure in Creating Payment Entry");
						responseBean = new ResponseBean(HttpStatus.BAD_REQUEST,
								pgResponseEntity.toString());
					} else {
						responseBean = new ResponseBean(HttpStatus.OK,
								pgResponseEntity.toString());
					}
				} else {
					pgResponseEntity.setStatus(Status.FAILURE.toString());
					pgResponseEntity.setPgTransactionId(trasactionId);
					pgResponseEntity.setBookingNo(orderInput.getOrderNo());
					pgResponseEntity.setAmountUnpaid(paymentDTO.getAmount());
					createPayment(pgResponseEntity);
					responseBean = new ResponseBean(HttpStatus.BAD_REQUEST,
							pgResponseEntity.toString());
					updateOrder(orderNo,Status.OPEN.toString());
				}
			}
			logger.info("Result of Payment: " + responseBean);

		} else {
			responseBean = new ResponseBean(HttpStatus.CONFLICT, productResult);
		}
		/** Removing lock from Map. */
		concurrentCheckMap.remove(orderInput.getOrderNo(),
				Status.INPROGRESS.toString());
		return responseBean;
	}

	public PgCode getPgCode(String pgCode) {
		if (PgCode.valueOf(pgCode) != null)
			return PgCode.valueOf(pgCode);
		else
			return null;
	}

	/*
	 * Create Payment:
	 */
	public int createPayment(PgResponseEntity pgResponseEntity) {
		ParseObject payment = new ParseObject("Payment");
		payment.put("paymentType", "WalletPayment");
		if (pgResponseEntity.getBookingNo() != null) {
			payment.put("paymentNo", pgResponseEntity.getBookingNo());

		} else {
			payment.put("paymentNo", "");
		}
		payment.put("amount", pgResponseEntity.getAmountPaid());
		payment.put("unPaidAmount", pgResponseEntity.getAmountUnpaid());
		/*
		 * Transaction is the unique key for every transaction.
		 */
		payment.put("transactionId", sessionIdGenerator
				.generateSessionId(pgResponseEntity.getPgTransactionId()));
		payment.put("status", pgResponseEntity.getStatus());
		payment.put("orderNo", pgResponseEntity.getBookingNo());

		try {
			payment.save();
			return 1;
		} catch (ParseException e) {
			logger.error(e);
			return 0;
		}
	}

	public void updateOrder(String orderNo, String status) {
		ParseObject order = new ParseObject("Order");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Order");
		query.whereEqualTo("OrderNo", orderNo);
		try {
			List<ParseObject> orders = query.find();
			order = orders.get(0);
			logger.info("Current status is: "+order.getString("Status") + " and Order id as: "+ order.getObjectId());
			logger.info("New Status will be: "+status);
			if (order != null) {
				order.put("Status", status);
				order.save();
			}
		} catch (ParseException e) {
			logger.error(e);
		}

	}
}
