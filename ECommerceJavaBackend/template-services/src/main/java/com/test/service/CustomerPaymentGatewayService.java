/**
 * 
 */
package com.test.service;

import com.test.persistence.entities.CustomerPaymentGateway;

public interface CustomerPaymentGatewayService extends CrudService<CustomerPaymentGateway>{
		CustomerPaymentGateway findByCustomerNoAndPgCode(String customerNo, String pgCode);
		CustomerPaymentGateway findByCustomerNoAndIsDefaultPgCode(String customerNo, Integer isDefaultPaymentGateway);
}
