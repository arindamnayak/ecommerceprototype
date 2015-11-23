package com.test.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class PgProperties extends BaseEntity {
	
	@Column(name = "pg_code",nullable=false)
	private String pgCode;
	
	@Column(name = "property_key",nullable=false)
	private String propertyKey;
	
	@Column(name = "property_value",nullable=false)
	private String propertyValue;

	

	/**
	 * @return the pgCode
	 */
	public String getPgCode() {
		return pgCode;
	}

	/**
	 * @param pgCode the pgCode to set
	 */
	public void setPgCode(String pgCode) {
		this.pgCode = pgCode;
	}

	/**
	 * @return the propertyKey
	 */
	public String getPropertyKey() {
		return propertyKey;
	}

	/**
	 * @param propertyKey the propertyKey to set
	 */
	public void setPropertyKey(String propertyKey) {
		this.propertyKey = propertyKey;
	}

	/**
	 * @return the propertyValue
	 */
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	

}
