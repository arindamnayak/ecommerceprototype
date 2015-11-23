package com.test.BackendService.dtos;

import com.test.Enums.PgCode;

public class PaymentDTO {

	 private String customerNo;
	 private String bookingNo;
	 private Integer status;
	 private PgCode pgCode;
	 private double amount; 
	 private String checkSum; // for each request identification 
	 private String comment;
	 private String currency;
	
	 /**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * @param customerNo the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	/**
	 * @return the pgCode
	 */
	public PgCode getPgCode() {
		return pgCode;
	}
	/**
	 * @param pgCode the pgCode to set
	 */
	public void setPgCode(PgCode pgCode) {
		this.pgCode = pgCode;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the bookingNo
	 */
	public String getBookingNo() {
		return bookingNo;
	}
	/**
	 * @param bookingNo the bookingNo to set
	 */
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}
	/**
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentDTO [customerNo=" + customerNo + ", bookingNo="
				+ bookingNo + ", status=" + status + ", pgCode=" + pgCode
				+ ", amount=" + amount + ", checkSum=" + checkSum
				+ ", comment=" + comment + ", currency=" + currency + "]";
	}
	 	 
	 
}
