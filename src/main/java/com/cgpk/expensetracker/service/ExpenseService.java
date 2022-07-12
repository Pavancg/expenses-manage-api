package com.cgpk.expensetracker.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cgpk.expensetracker.entity.Expense;

public interface ExpenseService {

	Page<Expense> getAllExpenses(Pageable pagable);
	
	Expense getExpenseById(Long id);
	
	void deleteById(Long id);
	
	Expense createExpense(Expense expense);
	
	Expense updateExpenseDetails(Long id,Expense expense);
	
	List<Expense> retriveListByCategory(String category,Pageable page);
	
	List<Expense> retriveListByName(String name,Pageable page);

}
