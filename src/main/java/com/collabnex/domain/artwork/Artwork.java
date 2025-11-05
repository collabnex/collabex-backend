package com.collabnex.domain.artwork;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="artworks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Artwork {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="owner_user_id")
    private User owner;

    @Column(nullable=false, length=200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name="price_minor")
    private Long priceMinor;

    @Column(length=3)
    private String currency;

    @Column(name="is_for_sale", nullable=false)
    private boolean forSale;

    @Column(name="is_listed", nullable=false)
    private boolean listed = true;

    @Column(name="likes_count", nullable=false)
    private Integer likesCount = 0;

    @Column(name="comments_count", nullable=false)
    private Integer commentsCount = 0;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;

    @Column(name="updated_at", nullable=false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }
    @PreUpdate
    public void preUpdate(){ updatedAt = Instant.now(); }
}
