package com.test.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by NiveditaP on 25-06-2015.
 */

@Entity
public class PaymentGateway extends BaseEntity {

   @Column(name="pg_code",nullable=false,unique=true)
	private String pgCode;
	
    private String name;
    private String url;
	/**
	 * @return the pgCode
	 */
	public String getPgCode() {
		return pgCode;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param pgCode the pgCode to set
	 */
	public void setPgCode(String pgCode) {
		this.pgCode = pgCode;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
    
	    
    
}
