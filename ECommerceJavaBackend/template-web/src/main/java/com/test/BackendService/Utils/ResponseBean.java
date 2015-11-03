package com.test.BackendService.Utils;

import org.springframework.stereotype.Component;

@Component
public class ResponseBean {

	private Object status;

	private Object message;

	public ResponseBean() {
	}	
	/**
	 * @return the status
	 */
	public Object getStatus() {
		return status;
	}

	/**
	 * @return the message
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Object status) {
		this.status = status;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Object message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponseBean [status=" + status + ", message=" + message + "]";
	}

	public ResponseBean(Object status, Object message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	

}
