package com.collabnex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.domain.market.ServiceEnquiry;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.ServiceEnquiryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/service-enquiries")
@RequiredArgsConstructor
public class ServiceEnquiryController {

    private final ServiceEnquiryService service;

    @PostMapping("/{serviceProductId}")
    public ResponseEntity<ServiceEnquiry> sendEnquiry(
            @PathVariable (name ="serviceProductId") Long serviceProductId,
            @RequestBody Map<String, String> body) {

        Long senderUserId = JwtUtil.getLoggedInUserId();

        String name = body.get("name");
        String phone = body.get("phone");
        String message = body.get("message");

        return ResponseEntity.ok(
            service.sendEnquiry(senderUserId, serviceProductId, name, phone, message)
        );
    }



    @GetMapping("/my")
    public ResponseEntity<List<ServiceEnquiry>> getMyEnquiries() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(service.getMyEnquiries(userId));
    }

    @GetMapping("/received")
    public ResponseEntity<List<ServiceEnquiry>> getReceivedEnquiries() {
        Long sellerId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(service.getReceivedEnquiries(sellerId));
    }
}
