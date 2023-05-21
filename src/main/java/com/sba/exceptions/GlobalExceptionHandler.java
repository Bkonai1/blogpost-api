package com.sba.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;
import org.springframework.validation.FieldError;

import com.sba.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse>resourceNotFoundHandler(ResourceNotFoundException ex){
		
		ApiResponse resp= new ApiResponse();
		resp.setMessage(ex.getMessage()+" and "+" status code: "+HttpStatus.NOT_FOUND);
		resp.setSuccess(false); 
		return new ResponseEntity<ApiResponse>(resp,HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>>methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
		
		Map<String,String>map= new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String field= ((FieldError)error).getField();
			String msg= error.getDefaultMessage();
			map.put(field, msg);
		} );
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
		
	}

}
