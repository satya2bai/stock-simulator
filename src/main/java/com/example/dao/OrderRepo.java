package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
	
	@Query(value = "SELECT * FROM stock_order u WHERE u.user_id = ?1", nativeQuery = true)
	List<Order> findOrdersByUserId(int id);

}
