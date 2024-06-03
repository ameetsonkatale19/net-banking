package com.nationalbank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nationalbank.model.Account;
import com.nationalbank.model.JwtRequest;
import com.nationalbank.model.JwtResponse;
import com.nationalbank.model.User;
import com.nationalbank.security.JwtHelper;
import com.nationalbank.service.AccountService;
import com.nationalbank.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtHelper helper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest jwtRequest){
		this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.helper.generateToken(userDetails);
		
		 // Fetch user account details
        User user = (User) userDetails; // Assuming UserDetails is implemented by User class
        Optional<Account> accountOpt = accountService.findAccountByUserId(user.getId());
        Long accountId = accountOpt.map(Account::getId).orElse(null);
        String accountNumber = accountOpt.map(Account::getAccountNumber).orElse(null);
		
        JwtResponse jwtResponse = JwtResponse.builder()
        	    .jwtToken(token)
        	    .accountId(accountId)
        	    .accountNumber(accountNumber)
        	    .username(userDetails.getUsername())
        	    .status("Success")
        	    .build();

        	return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

		
	}
	private void doAuthenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
		manager.authenticate(authentication);
		}
		catch(BadCredentialsException e) {
			throw new BadCredentialsException("Invalid UserName or Password");
		}
		
	}
	
	   @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<JwtResponse> exceptionHandler() {
		   JwtResponse jwtResponse = JwtResponse.builder()
					 .jwtToken("")
					 .username("")
					 .status("Failed").build();
	        return new ResponseEntity<>(jwtResponse, HttpStatus.UNAUTHORIZED);
	    }
	   
	   @PostMapping("/create-user")
	    public User CreateUser ( @RequestBody User user ) {
		  
		   return userService.CreateUser(user);
	    	
	    }
	   
	   
	   
	
}
