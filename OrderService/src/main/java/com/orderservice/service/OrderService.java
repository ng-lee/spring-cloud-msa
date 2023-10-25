package com.orderservice.service;

import com.orderservice.dto.OrderDto;
import com.orderservice.entity.Order;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    List<Order> getAllOrdersByMemberId(String memberId);
}
