package com.cgpk.expensetracker.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserModel {

	private Long id;

	@NotEmpty(message="name should not be empty")
	private String name;

	@NotNull(message="name should not be empty")
	private String email;
	
	@NotNull(message="name should not be empty")
	@Size(min=5,message="Password length should be minimum 5")
	private String password;

	private Long age = 0L;

}
