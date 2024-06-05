package com.nationalbank.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.nationalbank.dto.TransferRequest;
import com.nationalbank.model.Account;
import com.nationalbank.model.Transaction;
import com.nationalbank.model.TransactionType;
import com.nationalbank.model.User;
import com.nationalbank.repository.AccountRepository;
import com.nationalbank.repository.TransactionRepository;
import com.nationalbank.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
   
    
    @Transactional
    public Transaction transferToAccount(String username, String destinationAccountNumber, double amount, String receiverName, String remarks) {
        User user = userService.getUserByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Account sourceAccount = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Source account not found for the user"));

        Account destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (sourceAccount.getAccountNumber().equals(destinationAccount.getAccountNumber())) {
            throw new RuntimeException("Source and destination accounts cannot be the same");
        }
        if (sourceAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in source account");
        }

        logger.info("Transferring amount: " + amount + " from account: " + sourceAccount.getAccountNumber() + " to account: " + destinationAccount.getAccountNumber());

        
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(amount);
        transaction.setReceiverName(receiverName);
        transaction.setRemarks(remarks);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransaction_date(LocalDate.now());
        transactionRepository.save(transaction);
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        logger.info("Transaction completed successfully");
        
        return transaction;
    }
    
    @Transactional
    public Transaction deposit(String username, double amount) {
    	 User user = userRepository.findByUsername(username);
                 
         Account account = accountRepository.findByUser(user)
                 .orElseThrow(() -> new RuntimeException("Account not found"));
         System.out.println("account details : "+ account);
         account.setBalance(account.getBalance() + amount);


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransaction_date(LocalDate.now());
        
        transactionRepository.save(transaction);
        accountRepository.save(account);

        return transaction;
    }
    
   
    @Transactional
    public Transaction withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(account);
        transaction.setDestinationAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setTransaction_date(LocalDate.now());

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return transaction;
    }
    
    
    
}
