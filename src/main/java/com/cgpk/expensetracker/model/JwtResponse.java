package com.cgpk.expensetracker.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.cgpk.expensetracker.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtResponse {

	private String jwtTokenString;
}

