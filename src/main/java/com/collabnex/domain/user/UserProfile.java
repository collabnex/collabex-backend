package com.collabnex.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /* ---------- Core Info ---------- */

    @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;

    @Column(name = "domain", nullable = false, length = 160)
    private String domain;
    
    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "JSON")
    private String skills; // e.g. ["Spring Boot", "UI Design"]

    @Column(name = "profile_image_url", length = 512)
    private String profileImageUrl;

    @Column(name = "portfolio_url", length = 512)
    private String portfolioUrl;

    /* ---------- Search & Filter Fields ---------- */

    @Column(name = "profession", length = 100)
    private String profession; // e.g. "Photographer", "UI Designer"

    @Column(name = "availability_status", length = 50)
    private String availabilityStatus; // e.g. "Available", "Busy", "Looking for Projects"

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience; // e.g. 3

    @Column(name = "hourly_rate")
    private Double hourlyRate; // e.g. 45.0 (USD/hour or INR/hour depending on config)

    @Column(name = "tags", columnDefinition = "JSON")
    private String tags; // e.g. ["freelancer", "collaboration", "remote"]

    /* ---------- Location Fields ---------- */

    @Column(name = "location_text", length = 255)
    private String locationText; // human-readable (e.g. "Kochi, Kerala, India")

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    /* ---------- Collab-Specific ---------- */

    @Column(name = "collaboration_type", length = 100)
    private String collaborationType; 
    // e.g. "Freelance", "Open to Collaboration", "Looking for Team", "Mentor"

    @Column(name = "social_links", columnDefinition = "JSON")
    private String socialLinks; 
    // e.g. {"instagram":"url","linkedin":"url","twitter":"url"}

    @Column(name = "visibility")
    private Boolean visibility = true; // visible in search
}
