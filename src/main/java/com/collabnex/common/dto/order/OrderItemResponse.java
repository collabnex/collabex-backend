package com.collabnex.common.dto.order;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long itemId;
    private Long productId;
    private String productTitle;
    private Double price;
    private Integer quantity;
}
