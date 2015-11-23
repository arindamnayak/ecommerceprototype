package com.test.payment.pg;

import com.test.persistence.entities.PgRequestEntity;
import com.test.service.PgPropertiesService;

public interface PaymentGatewayOps {

	// To handle request form paymentGateway
		public PgResponseEntity doPayment(PgRequestEntity pgRequestEntity);  
		public PgResponseEntity sendPostRequest(PgRequestEntity pgRequestEntity);
		public String refundPayment(PgRequestEntity PgRequestEntity); //TODO
		public String inquirePayment(PgRequestEntity PgRequestEntity); //TODO
		public PgResponseEntity getBalance(PgRequestEntity pgRequestEntity); //TODO
		public String  sendGetRequest(PgRequestEntity PgRequestEntity); // TODO		
		void buildProp(PgPropertiesService pgProService,String pgCode);
		
}
