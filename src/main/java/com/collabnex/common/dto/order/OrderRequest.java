package com.collabnex.common.dto.order;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    private List<OrderItemRequest> items;

    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String pincode;
    private String city;
    private String state;
    private String country;

    private String currency = "INR"; // default
}