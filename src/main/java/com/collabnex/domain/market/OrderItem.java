package com.collabnex.domain.market;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each item belongs to one order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // The physical product being purchased
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private PhysicalProduct product;

    private Integer quantity;

    private Double price;
}
