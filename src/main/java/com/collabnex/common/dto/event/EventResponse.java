package com.collabnex.common.dto.event;

import lombok.Data;

import java.time.Instant;

@Data
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private String eventType;
    private Instant startDatetime;
    private Double ticketPrice;
    private Integer availableSeats;
}
