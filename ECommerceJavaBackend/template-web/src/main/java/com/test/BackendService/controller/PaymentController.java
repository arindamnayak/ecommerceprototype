package com.test.BackendService.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.BackendService.Utils.CryptoUtil;
import com.test.BackendService.Utils.ResponseBean;
import com.test.BackendService.dtos.PaymentDTO;
import com.test.payment.pg.PaymentGatewayOps;
import com.test.payment.pg.PaymentGatewayOpsFactory;
import com.test.payment.pg.PgResponseEntity;
import com.test.persistence.entities.CustomerPaymentGateway;
import com.test.persistence.entities.PgRequestEntity;
import com.test.service.CustomerPaymentGatewayService;
import com.test.service.PgPropertiesService;

@RestController
public class PaymentController {

	@Autowired
	ResponseBean responseBean;

	@Autowired
	PgRequestEntity pgRequestEntity;

	@Autowired
	PgResponseEntity pgResponseEntity;

	@Autowired
	CryptoUtil cryptoUtil;

	@Autowired
	private PgPropertiesService pgPropertiesService;

	@Autowired
	private CustomerPaymentGatewayService customerPaymentGatewayService;

	final static Logger logger = Logger.getLogger(PaymentController.class);

	public ResponseBean MakePayment(@RequestBody PaymentDTO paymentDTO) {
		/* request logging */
		// auditLogUtil.createAuditLog(paymentDTO.toString(),request);
		// fech PG properties data
		// log PG request and reponse object in audit table
		logger.info("Inside Payment Controller");
		responseBean = requestValidation(paymentDTO);
		logger.debug("Request obj received by make_payment" + paymentDTO);
		logger.info("Request obj received by make_payment " + paymentDTO);
		PaymentGatewayOpsFactory factory = new PaymentGatewayOpsFactory();
		PaymentGatewayOps gatewayOps = factory.CreatePaymentGateway(paymentDTO
				.getPgCode());
		if (paymentDTO.getCustomerNo() != null && paymentDTO.getAmount() > 0) {
			CustomerPaymentGateway customerPaymentGateway = new CustomerPaymentGateway();
			customerPaymentGateway = customerPaymentGatewayService
					.findByCustomerNoAndPgCode(paymentDTO.getCustomerNo(),
							paymentDTO.getPgCode().toString());
			/*
			 * pgRequestEntity.setAccessToken(customerPaymentGateway
			 * .getAuthToken());
			 */
			/*
			 * Decrypting the encrypted AuthToken and setting it to AccessToken
			 */
			pgRequestEntity.setAccessToken(cryptoUtil
					.decryptString(customerPaymentGateway.getAuthToken()));
			pgRequestEntity.setAmount(paymentDTO.getAmount());
			pgRequestEntity.setBookingId(paymentDTO.getBookingNo());
			pgRequestEntity.setComment(paymentDTO.getComment());
			pgRequestEntity.setCurrency(paymentDTO.getCurrency());
			pgRequestEntity
					.setCustomerId(paymentDTO.getCustomerNo().toString()); // customerNo
			pgRequestEntity.setPgCode(paymentDTO.getPgCode().toString()); // pgCode

			// from pg_properties table
			pgRequestEntity.setMerchantTransactionId(paymentDTO.getBookingNo()); // booking_no

			pgRequestEntity.setRequestUrl(customerPaymentGateway
					.getPaymentGateway().getUrl());
			logger.debug("Request obj doPayment" + pgRequestEntity);

			// set pg properties
			gatewayOps.buildProp(pgPropertiesService, paymentDTO.getPgCode()
					.toString());

			// make payment deduction request form respective PG
			pgResponseEntity = gatewayOps.doPayment(pgRequestEntity);

			responseBean.setStatus(pgResponseEntity);
			logger.info("Response obj doPayment " + responseBean.toString());
		}

		return responseBean;
	}

	// To check Balance on payment gateway
	public ResponseBean checkUserBalance(@RequestBody PaymentDTO paymentDTO) {

		logger.info("Request obj received by make_payment" + paymentDTO);
		// validate checkBalance Request check_balance
		if (paymentDTO.getCustomerNo() != null
				&& !paymentDTO.getPgCode().toString().isEmpty()) {
			PaymentGatewayOpsFactory factory = new PaymentGatewayOpsFactory();
			PaymentGatewayOps gatewayOps = factory
					.CreatePaymentGateway(paymentDTO.getPgCode()); // return
																	// Payment
																	// gateway
																	// entity

			CustomerPaymentGateway customerPaymentGateway = new CustomerPaymentGateway();
			customerPaymentGateway = customerPaymentGatewayService
					.findByCustomerNoAndPgCode(paymentDTO.getCustomerNo(),
							paymentDTO.getPgCode().toString()); /*
																 * validate if
																 * custoemr
																 * exists in db
																 */

			// set pgProperties
			gatewayOps.buildProp(pgPropertiesService, paymentDTO.getPgCode()
					.toString());
			if (customerPaymentGateway != null
					&& !customerPaymentGateway.getAuthToken().isEmpty()) {
				/*
				 * pgRequestEntity.setAccessToken(customerPaymentGateway.
				 * getAuthToken());
				 */
				pgRequestEntity.setAccessToken(cryptoUtil
						.decryptString(customerPaymentGateway.getAuthToken()));
			}

			// logger.info("checkBalance auth_token",
			// pgRequestEntity.getAccessToken());
			// only need to set access token for get balance
			pgResponseEntity = gatewayOps.getBalance(pgRequestEntity);
			if (pgResponseEntity.getErrorMessage() == null
					&& pgResponseEntity.getErrorType() == null) {
				responseBean.setStatus(pgResponseEntity);
			} else {
				responseBean.setStatus(pgResponseEntity);
				responseBean.setMessage(pgResponseEntity.getErrorMessage());
			}
		}

		return responseBean;
	}

	private ResponseBean requestValidation(PaymentDTO paymentDTO) {
		/*
		 * to validate request coming to payment gateway service must have
		 * checksum
		 */
		if (paymentDTO.getCheckSum() == null
				|| paymentDTO.getPgCode().toString() != null) {
			responseBean.setStatus(paymentDTO);
			responseBean.setMessage("Checksum not found");
		} else if (getChecksum(paymentDTO) != null
				&& paymentDTO.getCheckSum() != getChecksum(paymentDTO)) {
			responseBean.setStatus(paymentDTO);
			responseBean.setMessage("Checksum mismatch !!!");
		} else {
			responseBean.setMessage("VALID-REQUEST");
		}

		responseBean.setMessage("VALID-REQUEST"); // TODO bypassing checksum
													// check
		return responseBean;

	}

	private String getChecksum(PaymentDTO paymentDTO) {
		// genrate checksum
		// TODO
		return null;
	}

}
