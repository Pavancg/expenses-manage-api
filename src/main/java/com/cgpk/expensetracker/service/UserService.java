package com.cgpk.expensetracker.service;

import com.cgpk.expensetracker.entity.User;
import com.cgpk.expensetracker.model.UserModel;

public interface UserService {
	
	User createUser(UserModel usermodel);

	User readUser();
	
	void deleteUser();
	
	User getLoggedInUser();
}
