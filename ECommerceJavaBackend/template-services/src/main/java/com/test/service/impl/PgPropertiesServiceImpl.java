package com.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.persistence.entities.PgProperties;
import com.test.persistence.repositories.PgPropertiesRepository;
import com.test.service.PgPropertiesService;

@Service
public class PgPropertiesServiceImpl implements PgPropertiesService{

	@Autowired
	PgPropertiesRepository pgPropertiesRepository;
	
	@Override
	public PgProperties get(PgProperties entity) {
		return pgPropertiesRepository.findOne(entity.getId());
	}

	@Override
	public PgProperties save(PgProperties entity) {
		return pgPropertiesRepository.save(entity);
	}

	@Override
	public List<PgProperties> findByPgCode(String pgCode) {
		return pgPropertiesRepository.findByPgCode(pgCode);
	}

	

}
