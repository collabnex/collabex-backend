package com.collabnex.domain.market;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    
    @Query("""
    	       SELECT o FROM Order o 
    	       LEFT JOIN FETCH o.items i 
    	       LEFT JOIN FETCH i.product 
    	       WHERE o.user.id = :userId
    	       """)
    	List<Order> findAllOrdersWithItemsByUser(@Param("userId") Long userId);

}
