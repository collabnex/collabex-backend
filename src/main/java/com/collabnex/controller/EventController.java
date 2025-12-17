package com.collabnex.controller;

import com.collabnex.common.dto.event.EventCreateRequest;
import com.collabnex.common.dto.event.EventResponse;
import com.collabnex.domain.event.Event;
import com.collabnex.security.JwtUtil;
import com.collabnex.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateRequest request) {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(eventService.createEvent(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/my")
    public ResponseEntity<List<EventResponse>> getMyEvents() {
        Long userId = JwtUtil.getLoggedInUserId();
        return ResponseEntity.ok(eventService.getMyEvents(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }
}
