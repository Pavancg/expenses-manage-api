package com.cgpk.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cgpk.expensetracker.entity.User;
import com.cgpk.expensetracker.model.UserModel;
import com.cgpk.expensetracker.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/profile")
	private ResponseEntity<User> retriveUser(){
		return new ResponseEntity<User>(userService.readUser(), HttpStatus.OK);
	}
	
	@DeleteMapping("/deactivate")
	private void deleteUser() {
		userService.deleteUser();
	}
}
