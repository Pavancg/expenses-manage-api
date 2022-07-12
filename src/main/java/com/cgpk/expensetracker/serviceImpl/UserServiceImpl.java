package com.cgpk.expensetracker.serviceImpl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.stereotype.Service;

import com.cgpk.expensetracker.entity.User;
import com.cgpk.expensetracker.exceptions.ResourceAlreadyExistException;
import com.cgpk.expensetracker.exceptions.ResourceNotFoundException;
import com.cgpk.expensetracker.model.UserModel;
import com.cgpk.expensetracker.repository.UserRepository;
import com.cgpk.expensetracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserModel usermodel) {
		if (userRepo.existsByEmail(usermodel.getEmail())) {
			throw new ResourceAlreadyExistException("Email already exists");
		}
		User newUser = new User();
		BeanUtils.copyProperties(usermodel, newUser);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepo.save(newUser);
	}

	@Override
	public User readUser() {
		Optional<User> user = userRepo.findById(getLoggedInUser().getId());
		if (user.isPresent()) {
			return user.get();
		}
		throw new ResourceNotFoundException("User id not found");
	}

	@Override
	public void deleteUser() {

		userRepo.deleteById(getLoggedInUser().getId());
	}

	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email  = authentication.getName();
		
		return userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Resource not found "+email));
	}

}
