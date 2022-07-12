package com.cgpk.expensetracker.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.cgpk.expensetracker.entity.User;
import com.cgpk.expensetracker.model.AuthModel;
import com.cgpk.expensetracker.model.JwtResponse;
import com.cgpk.expensetracker.model.UserModel;
import com.cgpk.expensetracker.service.CustomeUserService;
import com.cgpk.expensetracker.service.UserService;
import com.cgpk.expensetracker.util.JwtTokenGenerator;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	CustomeUserService customeUserService;

	@Autowired
	JwtTokenGenerator tokenGenerator;

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception {

		authenticate(authModel.getEmail(), authModel.getPassword());

		UserDetails userDetails = customeUserService.loadUserByUsername(authModel.getEmail());
		return new ResponseEntity<JwtResponse>(new JwtResponse(tokenGenerator.getJwtToken(userDetails)), HttpStatus.OK);
	}

	private void authenticate(String userName, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		} catch (DisabledException deb) {
			throw new Exception("DisabledException credentials");
		} catch (BadRequest deb) {
			throw new Exception("Bad credentials");
		}
	}

	@PostMapping("/register")
	private ResponseEntity<User> createUser(@Valid @RequestBody UserModel user) {
		return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
	}

}
