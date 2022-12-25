package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepo;
import com.example.entity.CustomUserDetails;
import com.example.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		return new CustomUserDetails(user);
	}

}
