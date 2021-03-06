package com.test.persistence.entities;


import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class PgRequestEntity extends BaseEntity{
	
	@Column
	public String merchantTransactionId ;
	public Double amount;
	public String currency;
	public String comment;
	public String accessToken; // Authorization: Bearer 58bd7202-d0b8-41b9-be74-09981e0f86b2
	public String makePaymentAPIPath;
	public String requestUrl ;
	public String customerId;
	public String bookingId;
	public String responseUrl;
	public String pgCode; // to fetch pg_properties 

	/**
	 * @return the merchantTransactionId
	 */
	public String getMerchantTransactionId() {
		return merchantTransactionId;
	}
	/**
	 * @param merchantTransactionId the merchantTransactionId to set
	 */
	public void setMerchantTransactionId(String merchantTransactionId) {
		this.merchantTransactionId = merchantTransactionId;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	/**
	 * @return the makePaymentAPIPath
	 */
	public String getMakePaymentAPIPath() {
		return makePaymentAPIPath;
	}
	/**
	 * @param makePaymentAPIPath the makePaymentAPIPath to set
	 */
	public void setMakePaymentAPIPath(String makePaymentAPIPath) {
		this.makePaymentAPIPath = makePaymentAPIPath; 
	}
	/**
	 * @return the requestUrl
	 */
	public String getRequestUrl() {
		return requestUrl;
	}
	/**
	 * @param requestUrl the requestUrl to set
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the bookingId
	 */
	public String getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the responseUrl
	 */
	public String getResponseUrl() {
		return responseUrl;
	}
	/**
	 * @param responseUrl the responseUrl to set
	 */
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "PgRequestEntity [merchantTransactionId="
				+ merchantTransactionId + ", amount=" + amount + ", currency="
				+ currency + ", comment=" + comment + ", accessToken="
				+ accessToken + ", makePaymentAPIPath=" + makePaymentAPIPath
				+ ", requestUrl=" + requestUrl + ", customerId=" + customerId
				+ ", bookingId=" + bookingId + ", responseUrl=" + responseUrl
				+ ", pgCode=" + pgCode + "]";
	}
  
	
}
