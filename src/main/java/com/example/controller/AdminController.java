package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.AdminService;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String addNewUser(@RequestBody User user) {
		return service.addNewUser(user);
	}
	
	@RequestMapping(method = RequestMethod.PATCH)
	public String disableUser(int id) {
		return service.disableUser(id);
	}
}
