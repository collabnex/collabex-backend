package com.collabnex.service;

import java.util.List;

import com.collabnex.domain.market.PhysicalProduct;

public interface PhysicalProductService {
	PhysicalProduct addPhysicalProduct(PhysicalProduct product, Long userId);

	List<PhysicalProduct> getAll();
}
