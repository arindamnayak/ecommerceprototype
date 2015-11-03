package com.test.BackendService.dtos;

public class Product {
	
	public Integer qty;
	public String productId;
	
	/**
	 * @return the qty
	 */
	public Integer getQty() {
		return qty;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param qty the qty to set
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductQtyList [qty=" + qty + ", productId=" + productId + "]";
	}
	
}
