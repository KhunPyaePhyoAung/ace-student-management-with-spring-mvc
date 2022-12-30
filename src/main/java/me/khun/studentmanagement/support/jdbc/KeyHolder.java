package me.khun.studentmanagement.support.jdbc;

import java.math.BigDecimal;

public class KeyHolder {
	
	private BigDecimal key;

	public void setKey(BigDecimal key) {
		this.key = key;
	}
	
	public BigDecimal getKey() {
		return key;
	}
}
