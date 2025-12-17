package com.collabnex.controller;

import com.collabnex.common.dto.event.EventBookingResponse;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.EventBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-bookings")
@RequiredArgsConstructor
public class EventBookingController {

    private final EventBookingService bookingService;

    @PostMapping("/{eventId}")
    public ResponseEntity<Void> bookEvent(@PathVariable Long eventId) {
        Long userId = JwtUtil.getLoggedInUserId();
        bookingService.bookEvent(userId, eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventBookingResponse>> getMyBookings() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(bookingService.getMyBookings(userId));
    }

    @GetMapping("/received")
    public ResponseEntity<List<EventBookingResponse>> getBookingsReceived() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(bookingService.getBookingsReceived(userId));
    }
}
