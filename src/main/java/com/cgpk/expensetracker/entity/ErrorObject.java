package com.cgpk.expensetracker.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorObject {

	private Integer status_code;
	private String message;
	private Date time_stamp;
}
