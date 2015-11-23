package com.test.payment.pg;

import org.springframework.stereotype.Component;

@Component
public class PgResponseEntity {

		// response 
		public String customerNo; 
		public String bookingNo;
		public String pgTransactionId;
		public String status; 
		public String reasonStatus;
		public double amountPaid; 
		public double amountUnpaid; // id 
		public double balance;
		public String currency;
		public String date;
		
		// in case of error 
		public String errorType;
		public String errorMessage;
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
		 * @return the pgTransactionId
		 */
		public String getPgTransactionId() {
			return pgTransactionId;
		}
		/**
		 * @param pgTransactionId the pgTransactionId to set
		 */
		public void setPgTransactionId(String pgTransactionId) {
			this.pgTransactionId = pgTransactionId;
		}
		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}
		/**
		 * @return the reasonStatus
		 */
		public String getReasonStatus() {
			return reasonStatus;
		}
		/**
		 * @param reasonStatus the reasonStatus to set
		 */
		public void setReasonStatus(String reasonStatus) {
			this.reasonStatus = reasonStatus;
		}
		/**
		 * @return the amountPaid
		 */
		public double getAmountPaid() {
			return amountPaid;
		}
		/**
		 * @param amountPaid the amountPaid to set
		 */
		public void setAmountPaid(double amountPaid) {
			this.amountPaid = amountPaid;
		}
		/**
		 * @return the amountUnpaid
		 */
		public double getAmountUnpaid() {
			return amountUnpaid;
		}
		/**
		 * @param amountUnpaid the amountUnpaid to set
		 */
		public void setAmountUnpaid(double amountUnpaid) {
			this.amountUnpaid = amountUnpaid;
		}
		/**
		 * @return the balance
		 */
		public double getBalance() {
			return balance;
		}
		/**
		 * @param balance the balance to set
		 */
		public void setBalance(double balance) {
			this.balance = balance;
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
		 * @return the date
		 */
		public String getDate() {
			return date;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(String date) {
			this.date = date;
		}
		/**
		 * @return the errorType
		 */
		public String getErrorType() {
			return errorType;
		}
		/**
		 * @param errorType the errorType to set
		 */
		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}
		/**
		 * @return the errorMessage
		 */
		public String getErrorMessage() {
			return errorMessage;
		}
		/**
		 * @param errorMessage the errorMessage to set
		 */
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "PgResponseEntity [customerNo=" + customerNo
					+ ", bookingNo=" + bookingNo + ", pgTransactionId="
					+ pgTransactionId + ", status=" + status
					+ ", reasonStatus=" + reasonStatus + ", amountPaid="
					+ amountPaid + ", amountUnpaid=" + amountUnpaid
					+ ", balance=" + balance + ", currency=" + currency
					+ ", date=" + date + ", errorType=" + errorType
					+ ", errorMessage=" + errorMessage + "]";
		}
		
		
		
		
		
}
