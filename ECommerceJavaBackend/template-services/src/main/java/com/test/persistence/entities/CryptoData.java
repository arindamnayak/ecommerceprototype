package com.test.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CryptoData extends BaseEntity {
	
	@Column(name = "secret_key")
	private String secretKey;
	
	@Column(name = "code", unique = true)
	private String code;

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
