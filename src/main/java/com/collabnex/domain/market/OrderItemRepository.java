package com.collabnex.domain.market;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByProductUserId(Long userId); // seller orders
    @Query("""
    	       SELECT oi FROM OrderItem oi
    	       JOIN FETCH oi.order o
    	       JOIN FETCH oi.product p
    	       WHERE p.user.id = :sellerId
    	       """)
    	List<OrderItem> findReceivedOrders(@Param("sellerId") Long sellerId);

}
