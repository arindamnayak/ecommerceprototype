package com.test.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.persistence.entities.CryptoData;


@Repository
public interface CryptoDataRepository extends JpaRepository<CryptoData, Long> {
	
	CryptoData findByCode(String code);

}
