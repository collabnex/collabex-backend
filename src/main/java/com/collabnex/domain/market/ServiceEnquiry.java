package com.collabnex.domain.market;


import java.time.Instant;

import com.collabnex.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_enquiries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEnquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which service was enquired?
    @ManyToOne
    @JoinColumn(name = "service_product_id", nullable = false)
    private ServiceProduct serviceProduct;

    // Who sent the enquiry? (Buyer)
    @ManyToOne
    @JoinColumn(name = "sender_user_id", nullable = false)
    private User sender;

    // Buyer name provided in form
    @Column(nullable = false)
    private String buyerName;

    // Buyer phone provided in form
    @Column(nullable = false)
    private String buyerPhone;

    // Message or note from buyer
    @Column(columnDefinition = "TEXT")
    private String message;

    private Instant createdAt = Instant.now();
}
