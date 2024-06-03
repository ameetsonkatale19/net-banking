package com.nationalbank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nationalbank.dto.AccountRequest;
import com.nationalbank.dto.AccountResponse;
import com.nationalbank.dto.AmountRequest;
import com.nationalbank.model.Account;
import com.nationalbank.model.User;
import com.nationalbank.service.AccountService;
import com.nationalbank.service.UserService;
import com.nationalbank.util.LoggedinUser;

@RestController
@RequestMapping("/account")
public class AccountDetailsController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
    private UserService userService;
	

	
	@GetMapping
	public ResponseEntity<AccountResponse> getAllAccountDetails() {
		List<Account> accountDetailsList = accountService.getAllAccountDetails();
		AccountResponse accountDetails = new AccountResponse("accounts fetched sucessfully",accountDetailsList);
		return ResponseEntity.ok(accountDetails);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Account>> getAccountDetailsById(@PathVariable long id) {
		
		Optional<Account> accountDetails = accountService.getAccountDetailsById(id);
		if(accountDetails != null) {
			return ResponseEntity.ok(accountDetails);
			} else {
			return ResponseEntity.notFound().build();
			}
		}
	
	   @PostMapping
	   public ResponseEntity<Account> createAccountDetails(@RequestBody AccountRequest accountRequest) {
		   System.out.println("account data: "+ accountRequest.toString());
		   User user = userService.getUserById(accountRequest.getUserId());
	        if (user == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        Account account = new Account();
	        account.setAccountNumber(accountRequest.getAccountNumber());
	        account.setBranch(accountRequest.getBranch());
	        account.setIfscCode(accountRequest.getIfscCode());
	        account.setBalance(accountRequest.getBalance());
	        account.setUser(user);

	        Account createAccountDetails = accountService.createAccountDetails(account);
	        System.out.println(createAccountDetails.toString());
	        return ResponseEntity.ok(createAccountDetails);
	    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Account> updateAccountDetails(@PathVariable long id, @RequestBody Account account) {
		Account updateAccountDetails = accountService.updateAccountDetails(id, account);
		if(updateAccountDetails != null) {
			return ResponseEntity.ok(updateAccountDetails);
		} else {
		return ResponseEntity.notFound().build();
		}
		}
	@DeleteMapping("/{id}")
	public ResponseEntity<Account> deleteAccountDetails(@PathVariable long id) {
		boolean isDeleted = accountService.deleteAccountDetails(id);
		if(isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
		return ResponseEntity.notFound().build();
		}
		}

}
