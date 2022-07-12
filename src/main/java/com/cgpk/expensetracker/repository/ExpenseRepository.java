package com.cgpk.expensetracker.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgpk.expensetracker.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	Page findByUserIdAndCategory(Long userId, String category, Pageable page);

	Page findByUserIdAndNameContaining(Long userId, String keyword, Pageable page);

	Page<Expense> findByUserId(Long userId, Pageable page);

	Optional<Expense> findByUserIdAndId(Long userId, Long id);
}
