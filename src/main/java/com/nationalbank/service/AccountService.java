package com.nationalbank.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nationalbank.model.Account;
import com.nationalbank.model.Transaction;
import com.nationalbank.model.TransactionType;
import com.nationalbank.repository.AccountRepository;
import com.nationalbank.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<Account> getAllAccountDetails() {
		
		return accountRepository.findAll();
	}
	
	public Optional<Account> getAccountDetailsById(long id) {
		Optional<Account> optionalAccount =accountRepository.findById(id);
		return optionalAccount;
	}
	
	public Optional<Account> findAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Account createAccountDetails(Account account) {
        return accountRepository.save(account);
    }

	public Account updateAccountDetails(long id, Account account) {
		if(accountRepository.existsById(id))
		{
			account.setId(id);
			return accountRepository.save(account);
		} else {
			return null;
		}
		
	}

	public boolean deleteAccountDetails(long id) {
	    if(accountRepository.existsById(id))
	    {
	    	accountRepository.deleteById(id);
	    	return true;
	    } else { 
		return false;
	}
	
	}
	
}
