package com.collabnex.service.impl;

import com.collabnex.common.dto.event.EventBookingResponse;
import com.collabnex.domain.event.*;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;
import com.collabnex.service.EventBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventBookingServiceImpl implements EventBookingService {

    private final EventRepository eventRepo;
    private final EventBookingRepository bookingRepo;
    private final UserRepository userRepo;

    @Override
    public void bookEvent(Long userId, Long eventId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        EventBooking booking = EventBooking.builder()
                .event(event)
                .user(user)
                .ticketPrice(event.getTicketPrice())
                .build();

        bookingRepo.save(booking);

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepo.save(event);
    }

    @Override
    public List<EventBookingResponse> getMyBookings(Long userId) {
        return bookingRepo.findByUserId(userId)
                .stream().map(this::toDto).toList();
    }

    @Override
    public List<EventBookingResponse> getBookingsReceived(Long hostId) {
        return bookingRepo.findByEventHostId(hostId)
                .stream().map(this::toDto).toList();
    }

    private EventBookingResponse toDto(EventBooking b) {
        EventBookingResponse dto = new EventBookingResponse();
        dto.setBookingId(b.getId());
        dto.setEventId(b.getEvent().getId());
        dto.setEventTitle(b.getEvent().getTitle());
        dto.setTicketPrice(b.getTicketPrice());
        dto.setBookedAt(b.getCreatedAt());
        return dto;
    }
}
