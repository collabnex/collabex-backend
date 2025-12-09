package com.collabnex.common.dto.order;

import lombok.Data;
@Data
public class ServiceEnquiryResponse {
    private Long id;
    private Long serviceProductId;   // FIXED (was Integer)
    private String serviceTitle;
    private String buyerName;
    private String buyerPhone;
    private String message;
    private String createdAt;
}
