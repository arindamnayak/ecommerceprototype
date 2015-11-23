package com.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.CustomerPaymentGateway;
import com.test.persistence.repositories.CustomerPaymentGatewayRepository;
import com.test.persistence.repositories.PaymentGatewayRepository;
import com.test.service.CustomerPaymentGatewayService;
import com.test.service.UserService;


@Service("customerPaymentGatewayService")
public class CustomerPaymentGatewayServiceImpl implements
		CustomerPaymentGatewayService {

	@Autowired
	CustomerPaymentGatewayRepository customerPaymentGatewayRepository;
	
	@Autowired
	UserService userService;
		
	@Autowired
	PaymentGatewayRepository paymentGatewayRepo;

	@Override
	public CustomerPaymentGateway get(CustomerPaymentGateway entity) {

		return customerPaymentGatewayRepository.findByCustomerNoAndPaymentGateway(entity.getCustomerNo(),entity.getPaymentGateway());
	}

	@Override
	public CustomerPaymentGateway save(CustomerPaymentGateway entity) {
		
		return customerPaymentGatewayRepository.save(entity);
	}

	@Override
	public CustomerPaymentGateway findByCustomerNoAndPgCode(String customerNo,
			String pgCode) {		
		return customerPaymentGatewayRepository.findByCustomerNoAndPaymentGateway(Long.parseLong(customerNo), paymentGatewayRepo.findBypgCode(pgCode));
	}

	@Override
	public CustomerPaymentGateway findByCustomerNoAndIsDefaultPgCode(
			String customerNo, Integer isDefaultPaymentGateway) {
		
		return customerPaymentGatewayRepository.findByCustomerNoAndIsDefaultPaymentGateway(Long.parseLong(customerNo), isDefaultPaymentGateway);
	}

	
	
	
	
	

}
