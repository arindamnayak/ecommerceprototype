package com.test.service;

import com.test.persistence.entities.CryptoData;

public interface CryptoService  extends CrudService<CryptoData> {

	CryptoData findByCode(String code);
}
