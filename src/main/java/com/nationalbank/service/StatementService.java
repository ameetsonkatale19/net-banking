package com.nationalbank.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationalbank.model.Account;
import com.nationalbank.model.Transaction;
import com.nationalbank.repository.AccountRepository;
import com.nationalbank.repository.TransactionRepository;

@Service
public class StatementService {

	   @Autowired
	    private AccountRepository accountRepository;

	    @Autowired
	    private TransactionRepository transactionRepository;

	    public List<Transaction> getAccountStatement(String accountNumber, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
	        Account account = accountRepository.findByAccountNumber(accountNumber)
	                .orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));

	        if (startDate.isPresent() && endDate.isPresent()) {
	            return transactionRepository.findAllByAccountAndDateRange(account, startDate.get(), endDate.get());
	        } else {
	            return transactionRepository.findAllByAccount(account);
	        }
	    }
}
