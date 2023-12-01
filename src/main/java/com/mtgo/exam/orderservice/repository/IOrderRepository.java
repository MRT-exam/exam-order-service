package com.mtgo.exam.orderservice.repository;

import com.mtgo.exam.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
