package com.mtgo.exam.orderservice.repository;

import com.mtgo.exam.orderservice.enums.OrderStatus;
import com.mtgo.exam.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByRestaurantIdAndStatus(String restaurantId, OrderStatus orderStatus);

}
