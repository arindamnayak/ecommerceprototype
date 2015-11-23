package com.test.persistence.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

@Component
@Entity
public class CustomerPaymentGateway extends BaseEntity {

	@Column(name="is_default_payment_gateway",nullable=false)
	private Integer isDefaultPaymentGateway;
	
	@Column(name="auth_token",nullable=false)
	private String authToken;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiry_date",nullable=false)
	private Date expiryDate;

	@OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "payment_gateway_id")
	private PaymentGateway paymentGateway;

	@JoinColumn(name = "user_id")
	private Long customerNo;

	/**
	 * @return the isDefaultPaymentGateway
	 */
	public Integer getIsDefaultPaymentGateway() {
		return isDefaultPaymentGateway;
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @return the paymentGateway
	 */
	public PaymentGateway getPaymentGateway() {
		return paymentGateway;
	}

	/**
	 * @return the customerNo
	 */
	public Long getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param isDefaultPaymentGateway the isDefaultPaymentGateway to set
	 */
	public void setIsDefaultPaymentGateway(Integer isDefaultPaymentGateway) {
		this.isDefaultPaymentGateway = isDefaultPaymentGateway;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @param paymentGateway the paymentGateway to set
	 */
	public void setPaymentGateway(PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(Long customerNo) {
		this.customerNo = customerNo;
	}

	

}
