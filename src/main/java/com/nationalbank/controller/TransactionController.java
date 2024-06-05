package com.nationalbank.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nationalbank.dto.TransferRequest;
import com.nationalbank.model.Transaction;
import com.nationalbank.model.TransactionType;
import com.nationalbank.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	

	@PostMapping("/transfer/{destinationAccountNumber}")
	public ResponseEntity<Transaction> transferAmount(@PathVariable String destinationAccountNumber, @RequestBody TransferRequest transferRequest) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    double amount = transferRequest.getAmount();
	    String receiverName = transferRequest.getReceiverName();
	    String remarks = transferRequest.getRemarks();
	    Transaction createdTransaction = transactionService.transferToAccount(username, destinationAccountNumber, amount, receiverName,remarks);
	    return ResponseEntity.ok(createdTransaction);
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Transaction> depositAmount ( @RequestParam(value ="amount") double  amount){
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Transaction depositAmount = transactionService.deposit(username, amount);
		return ResponseEntity.ok(depositAmount);
	
	}
	

}
