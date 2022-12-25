package com.example.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dao.OrderRepo;
import com.example.dao.UserRepo;
import com.example.entity.Order;
import com.example.entity.OrderStatus;
import com.example.entity.User;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepo repo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${com.example.alphavantage.apikey}")
	private String API_KEY;

	public List<Order> getMyOrders() {
		int id = getCurrentUser().getId();
		List<Order> orders = repo.findOrdersByUserId(id);
		orders.forEach(order -> order.setUser(null));
		return orders;
	}
	
	private double getPrice(String symbol) {
		String uri = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" +
				symbol + "&apikey=" + API_KEY;
		String response = restTemplate.getForObject(uri, String.class);
		double price = 0;
		try {
			JSONObject obj = new JSONObject(response);
			obj = obj.getJSONObject("Time Series (Daily)");
			int minusDays = 0;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			while (true) {
				LocalDate dateObj = LocalDate.now().minusDays(minusDays);
				String key = dateObj.format(formatter);
				if (obj.has(key)) {
					price = obj.getJSONObject(key).getDouble("4. close");
					break;
				}
				minusDays++;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	private User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = null;
		if (principal instanceof UserDetails) {
		  email = ((UserDetails)principal).getUsername();
		} else {
		  email = principal.toString();
		}
		return userRepo.findByEmail(email);
	}

	public String buyOrder(Order order) {
		order.setUser(getCurrentUser());
		order.setBuy(getPrice(order.getSymbol()));
		order.setStatus(OrderStatus.OPEN);
		repo.save(order);
		return "Buy order executed";
	}

	public String sellOrder(int id) {
		Order order = repo.findById(id).get();
		String loggedInUserEmail = getCurrentUser().getEmail();
		if (!loggedInUserEmail.equals(order.getUser().getEmail()))
			return "You are not authorized to execute this sell order";
		order.setSell(getPrice(order.getSymbol()));
		order.setStatus(OrderStatus.COMPLETE);
		repo.save(order);
		return "Sell order executed";
	}
}
