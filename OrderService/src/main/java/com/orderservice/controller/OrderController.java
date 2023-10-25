package com.orderservice.controller;


import com.orderservice.dto.OrderDto;
import com.orderservice.entity.Order;
import com.orderservice.service.OrderService;
import com.orderservice.vo.RequestOrder;
import com.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in Order Service";
    }

    @PostMapping("/{user-id}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @RequestBody RequestOrder requestOrder,
            @PathVariable("user-id") String userId) {


        OrderDto orderDto = OrderDto.builder()
                .productId(requestOrder.getProductId())
                .qty(requestOrder.getQty())
                .unitPrice(requestOrder.getUnitPrice()).build();


        OrderDto order = orderService.createOrder(orderDto);
        orderDto.setMemberId(userId);

        ResponseOrder responseOrder = ResponseOrder.builder()
                .productId(order.getProductId())
                .qty(order.getQty())
                .unitPrice(order.getUnitPrice())
                .totalPrice(order.getTotalPrice()).build();

        return ResponseEntity.ok(responseOrder);
    }

    @GetMapping("/{memberId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("memberId") String memberId) {
        List<ResponseOrder> list = orderService.getAllOrdersByMemberId(memberId).stream().map(order -> ResponseOrder.builder()
                .productId(order.getProductId())
                .qty(order.getQty())
                .unitPrice(order.getUnitPrice())
                .totalPrice(order.getTotalPrice()).build()
        ).toList();

        return ResponseEntity.ok(list);
    }
}
