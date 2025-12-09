package com.collabnex.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.common.dto.order.AllOrdersResponse;
import com.collabnex.common.dto.order.OrderRequest;
import com.collabnex.common.dto.order.OrderResponse;
import com.collabnex.domain.market.Order;
import com.collabnex.domain.market.OrderItem;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(orderService.placeOrder(userId, request));
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
    @GetMapping("/all")
    public ResponseEntity<AllOrdersResponse> getAllOrders() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }


    @GetMapping("/received")
    public ResponseEntity<List<OrderItem>> getOrdersReceived() {
        Long sellerId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(orderService.getOrdersReceived(sellerId));
    }
}
