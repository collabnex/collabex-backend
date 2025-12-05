package com.collabnex.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.domain.market.PhysicalProduct;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.PhysicalProductService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/physical-products")
@RequiredArgsConstructor
public class PhysicalProductController {

    private final PhysicalProductService service;

    @PostMapping
    public ResponseEntity<PhysicalProduct> add(@RequestBody PhysicalProduct product) {

        Long userId = JwtUtil.getLoggedInUserId();  // <--- FIXED

        PhysicalProduct saved = service.addPhysicalProduct(product, userId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<PhysicalProduct>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
