package com.collabnex.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.domain.market.ServiceProduct;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.ServiceProductService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/service-products")
@RequiredArgsConstructor
public class ServiceProductController {

    private final ServiceProductService service;

    @PostMapping
    public ResponseEntity<ServiceProduct> add(@RequestBody ServiceProduct product) {

        Long userId = JwtUtil.getLoggedInUserId(); 

        ServiceProduct saved = service.addServiceProduct(product, userId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<ServiceProduct>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
