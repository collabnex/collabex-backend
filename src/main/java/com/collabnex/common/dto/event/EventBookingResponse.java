package com.collabnex.common.dto.event;

import lombok.Data;

import java.time.Instant;

@Data
public class EventBookingResponse {
    private Long bookingId;
    private Long eventId;
    private String eventTitle;
    private Double ticketPrice;
    private Instant bookedAt;
}
