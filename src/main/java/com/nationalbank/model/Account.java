package com.nationalbank.model;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String accountNumber;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@JoinColumn(name="fullName")
	private String fullName;
	
	@Column()
	private double balance;
	
	@Column()
	private String ifscCode;
	
	@Column(nullable = false)
	private String branch;
	
    @OneToMany(mappedBy = "sourceAccount")
    @JsonIgnore
    private Set<Transaction> sentTransactions;

    @OneToMany(mappedBy = "destinationAccount")
    @JsonIgnore
    private Set<Transaction> receivedTransactions;

    
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Set<Transaction> getSentTransactions() {
		return sentTransactions;
	}

	public void setSentTransactions(Set<Transaction> sentTransactions) {
		this.sentTransactions = sentTransactions;
	}

	public Set<Transaction> getReceivedTransactions() {
		return receivedTransactions;
	}

	public void setReceivedTransactions(Set<Transaction> receivedTransactions) {
		this.receivedTransactions = receivedTransactions;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


	public Account(Long id, String accountNumber, User user, String fullName, double balance, String ifscCode,
			String branch, Set<Transaction> sentTransactions, Set<Transaction> receivedTransactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.user = user;
		this.fullName = fullName;
		this.balance = balance;
		this.ifscCode = ifscCode;
		this.branch = branch;
		this.sentTransactions = sentTransactions;
		this.receivedTransactions = receivedTransactions;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


}
