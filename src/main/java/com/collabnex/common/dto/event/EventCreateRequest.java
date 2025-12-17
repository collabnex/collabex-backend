package com.collabnex.common.dto.event;

import com.collabnex.domain.event.EventType;
import lombok.Data;

import java.time.Instant;

@Data
public class EventCreateRequest {
    private String title;
    private String description;
    private EventType eventType;
    private Instant startDatetime;
    private Instant endDatetime;
    private String locationText;
    private String onlineLink;
    private Double ticketPrice;
    private Integer totalSeats;
}
