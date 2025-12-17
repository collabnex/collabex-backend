package com.collabnex.domain.event;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Host (Artist)
    @ManyToOne
    @JoinColumn(name = "host_user_id", nullable = false)
    private User host;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private EventType eventType; // ONLINE / OFFLINE

    private Instant startDatetime;
    private Instant endDatetime;

    private String locationText;
    private String onlineLink;

    private String bannerImageUrl;

    private Double ticketPrice;
    private String currency = "INR";

    private Integer totalSeats;
    private Integer availableSeats;

    @Enumerated(EnumType.STRING)
    private EventStatus status = EventStatus.PUBLISHED;

    private Instant createdAt = Instant.now();
}
