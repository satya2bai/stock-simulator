package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Order;
import com.example.service.OrderService;

@RestController
@RequestMapping("/v1/api/order")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Order> getMyOrders() {
		return service.getMyOrders();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String buyOrder(@RequestBody Order order) {
		return service.buyOrder(order);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PATCH)
	public String sellOrder(@PathVariable int id) {
		return service.sellOrder(id);
	}
}
