package com.nationalbank.dto;

import java.util.List;

import com.nationalbank.model.Account;

public class AccountResponse {

	private String message;
	private List<Account> accoutDetails;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Account> getAccoutDetails() {
		return accoutDetails;
	}
	public void setAccoutDetails(List<Account> accoutDetails) {
		this.accoutDetails = accoutDetails;
	}
	@Override
	public String toString() {
		return "AccountResponse [message=" + message + ", accoutDetails=" + accoutDetails + "]";
	}
	public AccountResponse(String message, List<Account> accoutDetails) {
		super();
		this.message = message;
		this.accoutDetails = accoutDetails;
	}
	public AccountResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}
