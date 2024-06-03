package com.nationalbank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nationalbank.model.User;
import com.nationalbank.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userservice;
	
	@GetMapping("/all")
	public List<User> getAllUsers(){
		return userservice.getAllUsers();
	}
	
	@GetMapping("/{id}")
	public User getAllUserById(@PathVariable Long id){
		return userservice.getUserById(id);
	}
}
