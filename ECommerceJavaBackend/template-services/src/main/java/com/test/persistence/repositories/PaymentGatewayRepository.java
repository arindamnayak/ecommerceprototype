package com.test.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.persistence.entities.PaymentGateway;




@Repository
public interface PaymentGatewayRepository  extends CrudRepository<PaymentGateway, Long>{
	
	PaymentGateway findBypgCode(String pgCode);	

}
