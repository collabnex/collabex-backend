
package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.market.Order;
import com.collabnex.domain.user.User;
import com.collabnex.service.OrderService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> create(@AuthenticationPrincipal User user, @RequestBody CreateOrder req) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.createSimpleOrder(user, req.getArtworkId(), req.getQty())));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Page<Order>>> mine(@AuthenticationPrincipal User user, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(orderService.myOrders(user, pageable)));
    }

    @Data public static class CreateOrder { private Long artworkId; private Integer qty = 1; }
}
