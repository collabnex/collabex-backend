package com.collabnex.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false, unique=true)
    private User user;

    @Column(name="full_name", nullable=false, length=160)
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "JSON")
    private String skills;

    @Column(name="profile_image_url", length=512)
    private String profileImageUrl;

    @Column(name="location_text", length=255)
    private String locationText;

    @Column(name="portfolio_url", length=512)
    private String portfolioUrl;
}
