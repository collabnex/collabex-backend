package com.collabnex.domain.market;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.collabnex.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ServiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String description;
    private BigDecimal price;

    private Integer deliveryTimeDays;
    private String category;
    private String imagePath;

    private LocalDateTime createdAt = LocalDateTime.now();
}
