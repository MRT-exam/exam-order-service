package com.mtgo.exam.orderservice.repository;

import com.mtgo.exam.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
