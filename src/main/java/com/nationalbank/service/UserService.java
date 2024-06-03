package com.nationalbank.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nationalbank.model.User;
import com.nationalbank.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByUsername(username);
		
		return user;
	}
	
	public User CreateUser (User user) {
		if (user.getPassword() == null) {
	        // Handle the case where the password is null
	        throw new IllegalArgumentException("Password cannot be null");
	    }
	   
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	    public User getUserById(Long userId) {
	        return userRepository.findById(userId).orElse(null);
	    }
	    
	    public User getUserByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }
	    
	    public List<User> getAllUsers(){
	    	    	return userRepository.findAll();
	    	    }
}
