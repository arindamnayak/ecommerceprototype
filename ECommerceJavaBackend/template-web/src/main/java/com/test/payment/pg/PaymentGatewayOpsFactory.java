package com.test.payment.pg;

import com.test.Enums.PgCode;
import com.test.payment.pg.integration.citrus.CitruspayPaymentGateway;


public class PaymentGatewayOpsFactory {

	public  PaymentGatewayOps CreatePaymentGateway(PgCode pgcode)
    {
	 PaymentGatewayOps gateway = null;
        
        switch(pgcode)
        {
            case CITRUSPAY:
                gateway =  new CitruspayPaymentGateway();
                break;
            default:
               gateway = new CitruspayPaymentGateway(); 
               break;     

           
        }

        return gateway;
    }
}
