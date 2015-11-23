package com.test.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.CryptoData;
import com.test.persistence.repositories.CryptoDataRepository;
import com.test.service.CryptoService;

@Service
public class CryptoServiceImpl implements CryptoService{

	@Autowired
	CryptoDataRepository cryptoDataRepository;
	
	@Override
	public CryptoData get(CryptoData entity) {
		return cryptoDataRepository.findOne(entity.getId());
	}

	@Override
	public CryptoData save(CryptoData entity) {
		return cryptoDataRepository.save(entity);
	}

	@Override
	public CryptoData findByCode(String code) {
		return cryptoDataRepository.findByCode(code);
	}

}
