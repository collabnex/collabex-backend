package com.collabnex.service.impl;

import com.collabnex.common.dto.event.EventCreateRequest;
import com.collabnex.common.dto.event.EventResponse;
import com.collabnex.domain.event.*;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;
import com.collabnex.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    @Override
    public Event createEvent(Long hostId, EventCreateRequest request) {

        User host = userRepo.findById(hostId)
                .orElseThrow(() -> new RuntimeException("User not found"));
       
        Event event = Event.builder()
                .host(host)
                .title(request.getTitle())
                .description(request.getDescription())
                .eventType(request.getEventType())
                .startDatetime(request.getStartDatetime())
                .endDatetime(request.getEndDatetime())
                .locationText(request.getLocationText())
                .onlineLink(request.getOnlineLink())
                .ticketPrice(request.getTicketPrice())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getTotalSeats())
                .build();
        event.setStatus(EventStatus.PUBLISHED);
        event.setCurrency("INR");
        return eventRepo.save(event);
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepo.findByStatus(EventStatus.PUBLISHED)
                .stream().map(this::toDto).toList();
    }

    @Override
    public List<EventResponse> getMyEvents(Long hostId) {
        return eventRepo.findByHostId(hostId)
                .stream().map(this::toDto).toList();
    }

//    @Override
//    public Event getEventById(Long eventId) {
//        return eventRepo.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//    }

    @Override
    public EventResponse getEventResponseById(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return toDto(event);
    }


    private EventResponse toDto(Event e) {
        EventResponse dto = new EventResponse();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());
//        dto.setEventType(e.getEventType().name());
        dto.setEventType(
                e.getEventType() != null ? e.getEventType().name() : null
        );

        dto.setStartDatetime(e.getStartDatetime());
        dto.setTicketPrice(e.getTicketPrice());
        dto.setAvailableSeats(e.getAvailableSeats());
        return dto;
    }
}
