package com.cgpk.expensetracker.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cgpk.expensetracker.entity.ErrorObject;

@ControllerAdvice
public class GlobalExceptionHandler extends  ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorObject> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest request){
	
		ErrorObject object = new ErrorObject();
		object.setStatus_code(HttpStatus.NOT_FOUND.value());
		object.setMessage(ex.getMessage());
		object.setTime_stamp(new Date());
		return new ResponseEntity<ErrorObject>(object, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorObject> handleArgumentMismatcException(MethodArgumentTypeMismatchException ex,WebRequest request){
		ErrorObject object = new ErrorObject();
		object.setStatus_code(HttpStatus.BAD_REQUEST.value());
		object.setMessage(ex.getMessage());
		object.setTime_stamp(new Date());
		return new ResponseEntity<ErrorObject>(object, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorObject> handleGeneralExceptions(Exception ex,WebRequest request){
		ErrorObject object = new ErrorObject();
		object.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
		object.setMessage(ex.getMessage());
		object.setTime_stamp(new Date());
		return new ResponseEntity<ErrorObject>(object, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		Map<String, Object> mapObj = new HashMap();
		mapObj.put("status_code", status.BAD_GATEWAY.value());
		List<String> list =ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.toList());
		mapObj.put("message", list.toString());
		mapObj.put("timestamp", new  Date());
		return new ResponseEntity(mapObj, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ErrorObject> handleResourceAlreadyExistException(ResourceAlreadyExistException ex, WebRequest request){
	
		ErrorObject object = new ErrorObject();
		object.setStatus_code(HttpStatus.CONFLICT.value());
		object.setMessage(ex.getMessage());
		object.setTime_stamp(new Date());
		return new ResponseEntity<ErrorObject>(object, HttpStatus.CONFLICT);
	}
	
}
