package com.collabnex.domain.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventBookingRepository extends JpaRepository<EventBooking, Long> {

    List<EventBooking> findByUserId(Long userId);

    List<EventBooking> findByEventHostId(Long hostId);
}
