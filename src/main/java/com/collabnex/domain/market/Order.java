
package com.collabnex.domain.market;

import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="buyer_user_id")
    private User buyer;

    @Column(nullable=false, length=16)
    private String status;

    @Column(name="subtotal_minor", nullable=false) private Long subtotalMinor = 0L;
    @Column(name="discount_minor", nullable=false) private Long discountMinor = 0L;
    @Column(name="total_minor", nullable=false) private Long totalMinor = 0L;
    @Column(nullable=false, length=3) private String currency;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
    @Column(name="updated_at", nullable=false) private Instant updatedAt;

    @PrePersist public void pre(){ Instant now = Instant.now(); createdAt = now; updatedAt = now; }
    @PreUpdate public void up(){ updatedAt = Instant.now(); }
}
