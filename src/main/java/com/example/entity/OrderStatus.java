package com.example.entity;

public enum OrderStatus {
	OPEN("open"), COMPLETE("complete"), CANCELLED("cancelled");
	
	private String status;
	
	private OrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
