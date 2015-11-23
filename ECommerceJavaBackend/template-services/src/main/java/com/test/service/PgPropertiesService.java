package com.test.service;

import java.util.List;

import com.test.persistence.entities.PgProperties;

public interface PgPropertiesService  extends CrudService<PgProperties>{

	List<PgProperties> findByPgCode(String pgCode);
}
