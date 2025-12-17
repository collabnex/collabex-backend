package com.collabnex.service;

import com.collabnex.common.dto.event.EventCreateRequest;
import com.collabnex.common.dto.event.EventResponse;
import com.collabnex.domain.event.Event;

import java.util.List;

public interface EventService {

    Event createEvent(Long hostId, EventCreateRequest request);

    List<EventResponse> getAllEvents();

    List<EventResponse> getMyEvents(Long hostId);

    Event getEventById(Long eventId);
}