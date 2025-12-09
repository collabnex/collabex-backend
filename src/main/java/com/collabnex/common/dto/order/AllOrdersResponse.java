package com.collabnex.common.dto.order;

import java.util.List;

import lombok.Data;

@Data
public class AllOrdersResponse {
    private List<OrderResponse> physicalOrders;
    private List<ServiceEnquiryResponse> serviceEnquiries;
}

