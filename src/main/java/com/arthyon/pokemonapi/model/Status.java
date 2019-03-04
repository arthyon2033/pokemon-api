package com.arthyon.pokemonapi.model;

public class Status {
	
	private String status;
	private int value;
	
	public Status(String status, int value) {
		this.status = status;
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
