package com.nationalbank.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private double amount;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate transaction_date;
	
    private String receiverName;
    
    private String remarks;
    
	@ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;
    
	    @ManyToOne
	    @JoinColumn(name = "destination_account_id")
	    private Account destinationAccount;
	    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public LocalDate getTransaction_date() {
		return transaction_date;
	}

	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}

	public Account getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Account sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Account getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(Long id, double amount, TransactionType transactionType, LocalDate transaction_date,
			Account sourceAccount, Account destinationAccount, String receiverName, String remarks) {
		super();
		this.id = id;
		this.amount = amount;
		this.transactionType = transactionType;
		this.transaction_date = transaction_date;
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.receiverName = receiverName;
		this.remarks= remarks;
	}


	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", amount=" + amount + ", transactionType=" + transactionType
				+ ", transaction_date=" + transaction_date + ", sourceAccount=" + sourceAccount
				+ ", destinationAccount=" + destinationAccount + ", receiverName=" + receiverName + ", remarks="
				+ remarks + "]";
	}


	
}
