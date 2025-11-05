package com.collabnex.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=96)
    private String slug;

    @Column(nullable=false, length=160)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name="is_active", nullable=false)
    private boolean active = true;
}
