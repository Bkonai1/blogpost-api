package com.sba.services.impl;
import com.sba.repositories.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

import com.sba.payloads.UserDto;
import com.sba.entities.User;
import com.sba.exceptions.ResourceNotFoundException;
import com.sba.services.UserService;

import net.bytebuddy.asm.Advice.This;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelmap;
	
	@Override
	public UserDto createUser(UserDto user) {
		User user1= this.dtoToUser(user);
		User savedUser= this.userRepo.save(user1); 
		
		return this.userToDto(savedUser); 
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {
		User u= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		u.setId(user.getId());
		u.setName(user.getName()); 
		u.setPassword(user.getPassword()); 
		u.setAbout(user.getAbout()); 
		u.setEmail(user.getEmail());
		u=this.userRepo.save(u);
		UserDto savedUser= this.userToDto(u);
		return savedUser;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User u= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		System.out.println(u.toString());
		UserDto savedUser= this.userToDto(u);
		return savedUser;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<UserDto> userList= this.userRepo.findAll().stream().map((u)-> this.userToDto(u)).collect(Collectors.toList()); 
		return userList;
	}

	@Override
	public void deleteUser(Integer userId) {

		User u= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		this.userRepo.delete(u);
	}
	
	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelmap.map(userDto, User.class); 
		
	/*	user.setId(userDto.getId());
		user.setName(userDto.getName()); 
		user.setPassword(userDto.getPassword()); 
		user.setAbout(userDto.getAbout()); 
		user.setEmail(userDto.getEmail()); */
		
		return user;
	}
	
    public UserDto userToDto(User user) {
    	UserDto userDto= this.modelmap.map(user, UserDto.class);
    	
   /* 	userDto.setAbout(user.getAbout()); 
    	userDto.setEmail(user.getEmail()); 
    	userDto.setId(user.getId()); 
    	userDto.setName(user.getName()); 
    	userDto.setPassword(user.getPassword());    */
    	
    	return userDto;
		
	}
}
