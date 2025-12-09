package com.collabnex.common.dto.order;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String fullName;
    private String city;
    private Instant createdAt;
    private List<OrderItemResponse> items;
}
