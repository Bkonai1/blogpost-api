package com.sba.services.impl;

import com.sba.entities.User;
import com.sba.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sba.repositories.UserRepo;

@Service
public class CustomerDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User user= this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user with username",username,0));
		return user;
	}

}
