package com.jsf.demo;

import java.math.BigDecimal;

public class Recommendation {

	private String carrierName;
	private String productName;
	private BigDecimal premium = BigDecimal.ZERO;

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

}
