package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dao.UserRepo;
import com.example.entity.User;

@Service
public class AdminService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public String addNewUser(User user) {
		repo.save(user);
		return "New user added";
	}
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public String disableUser(int id) {
		//TODO: implement enable/disable user
		return "This feature is yet to be implemented";
	}
}
