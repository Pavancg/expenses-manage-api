package com.cgpk.expensetracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgpk.expensetracker.entity.Expense;
import com.cgpk.expensetracker.service.ExpenseService;
import com.cgpk.expensetracker.service.UserService;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService service;

	
	@GetMapping("/expense")
	public List<Expense> getAllExpenses(Pageable page) {
		return service.getAllExpenses(page).toList();
	}

	@GetMapping("/expense/{id}")
	public Expense getExpenseByid(@PathVariable Long id) {
		return service.getExpenseById(id);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expense")
	public void deleteExpenseById(@RequestParam Long id) {
		service.deleteById(id);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expense")
	public Expense createExpenses(@Valid @RequestBody Expense expense) {
	
		return service.createExpense(expense);
	}

	@ResponseStatus(value = HttpStatus.CREATED)
	@PutMapping("/expense/{id}")
	public Expense createExpense(@RequestBody Expense expense, @PathVariable Long id) {
		return service.updateExpenseDetails(id, expense);
	}

	@GetMapping("/expense/category")
	public List<Expense> retriveByCategoty(@RequestParam String category, Pageable page) {
		return service.retriveListByCategory(category, page);
	}

	@GetMapping("/expense/name")
	public List<Expense> retriveByName(@RequestParam String name, Pageable page) {
		return service.retriveListByName(name, page);
	}
}
