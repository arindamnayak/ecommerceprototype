package com.test.BackendService.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.BackendService.Utils.ResponseBean;
import com.test.BackendService.dtos.OrderInput;
import com.test.BackendService.dtos.Product;

@RestController
@RequestMapping({ "/api/v1", "" })
public class PaymentController {

	/*
	 * 
	 * public PaymentController(String productName) {
	 * this.productName=productName; }
	 */
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ResponseBean displayMessage() throws Exception {
		System.out.println("Getting hit for displayMessage");
		OrderInput orderInput= new OrderInput();
		orderInput.setOrderId("12345");
		List<Product> productList= new ArrayList<Product>();
		Product pr= new Product();
		pr.setProductId("123456");
		pr.setQty(5);
		productList.add(pr);
		orderInput.setProductList(productList);
		
		JSONObject obj = new JSONObject();
		/*obj.put("orderId","d47rWnbZ7q");
		obj.put("productList",productList);*/
		obj.put("orderInput", orderInput);		
		
		return new ResponseBean(HttpStatus.OK, obj);		
		
	}

	@Transactional
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseBean updateOrder(@RequestBody OrderInput orderInput)
			throws Exception {
		System.out.println("Getting hit for updateOrder");
		String productName = null;
		Integer availableQty = null;
		Map<String, Integer> productResult = new HashMap<String, Integer>();
		boolean allItemsReady = true;

		System.out.println("OrderId requested is: " + orderInput.getOrderId());

		List<Product> productList = orderInput.getProductList();
		System.out.println("No of products in the list is : "
				+ productList.size());

		ParseObject Product = new ParseObject("Product");
		List<ParseObject> products= new ArrayList<ParseObject>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");

		for (Product product : productList) {
			System.out.println("ProductId requested is: "
					+ product.getProductId());
			System.out.println("No of Qty ordered is: : " + product.getQty());

			query.whereEqualTo("objectId", product.getProductId());

			Integer newQty = 0;

			try {
				List<ParseObject> productDetails = query.find();
				if (productDetails != null) {

					Product = productDetails.get(0);
					
					
					productName = Product.get("productName").toString();
					availableQty = Integer.parseInt(Product.get("availableQty")
							.toString());

					System.out.println("Product retrieved is: " + productName);
					System.out
							.println("Available Qty for the Product retrieved is: "
									+ availableQty);

					if (availableQty > 0 && availableQty >= product.getQty()) {
						newQty = availableQty - product.getQty();
						Product.put("availableQty", newQty.toString());
						products.add(Product);
						//Product.save();
					} else {
						productResult.put(product.getProductId(), availableQty);
						allItemsReady = false;
					}
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		
		if (allItemsReady) {
			for(ParseObject pObj :products)
			{
				pObj.save();
			}	
			
			
			return new ResponseBean(HttpStatus.OK,
					"Products Updated Successfully");
		} else {
			return new ResponseBean(HttpStatus.CONFLICT, productResult);
		}

		/*
		 * query.whereEqualTo("objectId",orderInput.getProductId());
		 * /*query.findInBackground(new FindCallback<ParseObject>() { public
		 * void done(List<ParseObject> list, ParseException e) { if (e == null)
		 * { System.out.println("List length: "+list.get(0));
		 * productName=list.get(0).getString("productName");
		 * System.out.println("productName is: "+productName); printValue(); }
		 * else { e.printStackTrace(); } }
		 * 
		 * 
		 * } );
		 */

		/*
		 * query.getInBackground("5qOzqTqaeg", new GetCallback<ParseObject>() {
		 * query.getFirstInBackground(new GetCallback() { public void
		 * done(ParseObject object, ParseException e) { if (e == null) {
		 * productName=object.getString("productName");
		 * System.out.println("productName is: "+productName); PaymentController
		 * pt = new PaymentController(object.getString("productName"));
		 * PaymentController pt= new PaymentController();
		 * pt.setProductName(object.getString("productName"));
		 * 
		 * } else { e.printStackTrace(); } } });
		 */

	}
}
