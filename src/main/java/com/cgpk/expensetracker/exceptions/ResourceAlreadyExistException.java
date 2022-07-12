package com.cgpk.expensetracker.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8721648027973521726L;
	
	public ResourceAlreadyExistException(String message) {
		super(message);
	}

}
