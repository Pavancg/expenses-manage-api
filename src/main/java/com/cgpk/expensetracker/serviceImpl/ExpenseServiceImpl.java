package com.cgpk.expensetracker.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cgpk.expensetracker.entity.Expense;
import com.cgpk.expensetracker.exceptions.ResourceNotFoundException;
import com.cgpk.expensetracker.repository.ExpenseRepository;
import com.cgpk.expensetracker.service.ExpenseService;
import com.cgpk.expensetracker.service.UserService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private UserService userService;
	
	@Override
	public Page<Expense> getAllExpenses(Pageable pageable) {
		return expenseRepository.findByUserId(userService.getLoggedInUser().getId(), pageable);
	}

	@Override
	public Expense getExpenseById(Long id) {
		Optional<Expense> expense = expenseRepository.findByUserIdAndId(userService.getLoggedInUser().getId(),id);
		if (expense.isPresent()) {
			return expense.get();
		}
		throw new ResourceNotFoundException("Expense is not found for the id : " + id);
	}

	@Override
	public void deleteById(Long id) {
		expenseRepository.deleteById(id);
	}

	@Override
	public Expense createExpense(Expense expense) {
		expense.setUser(userService.getLoggedInUser());
		return expenseRepository.save(expense);
	}

	@Override
	public Expense updateExpenseDetails(Long id, Expense expense) {
         Expense existingExpense = getExpenseById(id);
         existingExpense.setName(expense.getName() !=null? expense.getName() : existingExpense.getName());
         existingExpense.setCategory(expense.getCategory() !=null? expense.getCategory() : existingExpense.getCategory());
         existingExpense.setAmount(expense.getAmount() !=null? expense.getAmount() : existingExpense.getAmount());
         existingExpense.setDate(expense.getDate() !=null? expense.getDate() : existingExpense.getDate());
         existingExpense.setDescription(expense.getDescription() !=null? expense.getDescription() : existingExpense.getDescription());
         return expenseRepository.save(existingExpense);
	}

	@Override
	public List<Expense> retriveListByCategory(String category, Pageable page) {
		return expenseRepository.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category, page).toList();
	}

	@Override
	public List<Expense> retriveListByName(String name, Pageable page) {
		return expenseRepository.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),name, page).toList();
	}


}
