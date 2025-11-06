
package com.collabnex.service;

import com.collabnex.domain.market.Order;
import com.collabnex.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Order createSimpleOrder(User buyer, Long artworkId, Integer qty);
    Page<Order> myOrders(User buyer, Pageable pageable);
}
