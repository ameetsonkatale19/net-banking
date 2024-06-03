package com.nationalbank.dto;

public class AccountRequest {

	private String accountNumber;
	private String branch;
	private String ifscCode;
    private double balance;
    private Long userId;
   
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	@Override
	public String toString() {
		return "AccountRequest [accountNumber=" + accountNumber + ", branch=" + branch + ", ifscCode=" + ifscCode
				+ ", balance=" + balance + ", userId=" + userId + "]";
	}

}
