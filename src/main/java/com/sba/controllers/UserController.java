package com.sba.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sba.payloads.ApiResponse;
import com.sba.payloads.UserDto;
import com.sba.services.UserService;

@RestController
@RequestMapping("v1/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
		
		UserDto cretedUser= this.userService.createUser(user);
		return new ResponseEntity<>(cretedUser,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<UserDto> findUserId(@PathVariable Integer id ){
		
		UserDto cretedUser= this.userService.getUserById(id);
		return new ResponseEntity<UserDto>(cretedUser,HttpStatus.FOUND);
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto user,@PathVariable Integer id){
		
		UserDto cretedUser= this.userService.updateUser(user, id); 
		return ResponseEntity.ok(cretedUser); 
		
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
		
		this.userService.deleteUser(id); 
		return new ResponseEntity<ApiResponse>(new ApiResponse("User "+id+" deleted succesfully",true),HttpStatus.OK);
		
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<UserDto>> findAllUser( ){
		
		List<UserDto> cretedUser= this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(cretedUser,HttpStatus.FOUND);
		
	}

}


