package com.collabnex.service;

import com.collabnex.common.dto.event.EventBookingResponse;

import java.util.List;

public interface EventBookingService {

    void bookEvent(Long userId, Long eventId);

    List<EventBookingResponse> getMyBookings(Long userId);

    List<EventBookingResponse> getBookingsReceived(Long hostId);
}