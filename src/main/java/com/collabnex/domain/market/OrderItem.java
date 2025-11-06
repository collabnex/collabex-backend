
package com.collabnex.domain.market;

import com.collabnex.domain.artwork.Artwork;
import com.collabnex.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne(optional=false) @JoinColumn(name="artwork_id")
    private Artwork artwork;

    @ManyToOne(optional=false) @JoinColumn(name="seller_user_id")
    private User seller;

    @Column(nullable=false) private Integer quantity;
    @Column(name="unit_price_minor", nullable=false) private Long unitPriceMinor;
    @Column(name="line_total_minor", nullable=false) private Long lineTotalMinor;
}
