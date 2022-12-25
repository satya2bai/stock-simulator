package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepo;
import com.example.entity.AuthRequest;
import com.example.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public String getToken(AuthRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid email/password");
		}
		return jwt.generateToken(request.getEmail());
	}

}
