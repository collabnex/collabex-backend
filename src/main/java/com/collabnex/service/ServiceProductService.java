package com.collabnex.service;

import java.util.List;

import com.collabnex.domain.market.ServiceProduct;

public interface ServiceProductService {
    ServiceProduct addServiceProduct(ServiceProduct product, Long userId);
    List<ServiceProduct> getAll();
}
