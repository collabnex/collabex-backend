package com.collabnex.service;

import com.collabnex.common.dto.order.AllOrdersResponse;
import com.collabnex.common.dto.order.OrderRequest;
import com.collabnex.common.dto.order.OrderResponse;
import com.collabnex.domain.market.Order;
import com.collabnex.domain.market.OrderItem;
import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId, OrderRequest request);
    AllOrdersResponse getAllOrders(Long userId);


    public List<OrderResponse> getOrdersByUser(Long userId);
    List<OrderItem> getOrdersReceived(Long sellerId); // orders for MY products
}
