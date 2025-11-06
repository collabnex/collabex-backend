
package com.collabnex.domain.market;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="order_id")
    private Order order;

    @Column(nullable=false, length=16) private String provider; // RAZORPAY
    @Column(name="provider_payment_id", nullable=false, length=191) private String providerPaymentId;
    @Column(name="amount_minor", nullable=false) private Long amountMinor;
    @Column(nullable=false, length=3) private String currency;
    @Column(nullable=false, length=16) private String status;
    @Column(length=64) private String method;

    @Column(name="created_at", nullable=false, updatable=false) private Instant createdAt;
    @Column(name="updated_at", nullable=false) private Instant updatedAt;

    @PrePersist public void pre(){ Instant now = Instant.now(); createdAt = now; updatedAt = now; }
    @PreUpdate public void up(){ updatedAt = Instant.now(); }
}
