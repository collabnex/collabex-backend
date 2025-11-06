
package com.collabnex.service.impl;

import com.collabnex.common.exception.NotFoundException;
import com.collabnex.domain.artwork.Artwork;
import com.collabnex.domain.artwork.ArtworkRepository;
import com.collabnex.domain.market.*;
import com.collabnex.domain.user.User;
import com.collabnex.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final ArtworkRepository artworkRepo;

    @Override @Transactional
    public Order createSimpleOrder(User buyer, Long artworkId, Integer qty) {
        Artwork a = artworkRepo.findById(artworkId).orElseThrow(() -> new NotFoundException("Artwork not found"));
        long unit = (a.getPriceMinor() == null ? 0L : a.getPriceMinor());
        long subtotal = unit * (qty == null ? 1 : qty);
        Order o = Order.builder()
                .buyer(buyer)
                .status("PENDING")
                .subtotalMinor(subtotal).discountMinor(0L).totalMinor(subtotal)
                .currency(a.getCurrency() == null ? "INR" : a.getCurrency())
                .build();
        o = orderRepo.save(o);
        OrderItem item = OrderItem.builder()
                .order(o).artwork(a).seller(a.getOwner())
                .quantity(qty == null ? 1 : qty)
                .unitPriceMinor(unit)
                .lineTotalMinor(subtotal)
                .build();
        itemRepo.save(item);
        return o;
    }

    @Override @Transactional(readOnly = true)
    public Page<Order> myOrders(User buyer, Pageable pageable) {
        return orderRepo.findByBuyerIdOrderByCreatedAtDesc(buyer.getId(), pageable);
    }
}
