package com.test.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.persistence.entities.PgProperties;

@Repository
public interface PgPropertiesRepository extends
		JpaRepository<PgProperties, Long>
// , JpaSpecificationExecutor<PgProperties>
{
	List<PgProperties> findByPgCode(String pgCode);
}
