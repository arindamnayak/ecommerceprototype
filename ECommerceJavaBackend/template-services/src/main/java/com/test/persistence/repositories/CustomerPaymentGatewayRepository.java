package com.test.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.persistence.entities.CustomerPaymentGateway;
import com.test.persistence.entities.PaymentGateway;

@Repository
public interface CustomerPaymentGatewayRepository extends JpaRepository<CustomerPaymentGateway, Long> {

CustomerPaymentGateway findByCustomerNoAndPaymentGateway(Long customerNo, PaymentGateway paymentGateway);
CustomerPaymentGateway findByCustomerNoAndIsDefaultPaymentGateway(Long customerNo, Integer isDefaultPaymentGateway);		
		
}
