package com.collabnex.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.collabnex.common.dto.order.AllOrdersResponse;
import com.collabnex.common.dto.order.OrderItemRequest;
import com.collabnex.common.dto.order.OrderItemResponse;
import com.collabnex.common.dto.order.OrderRequest;
import com.collabnex.common.dto.order.OrderResponse;
import com.collabnex.common.dto.order.ServiceEnquiryResponse;
import com.collabnex.domain.market.Order;
import com.collabnex.domain.market.OrderItem;
import com.collabnex.domain.market.OrderItemRepository;
import com.collabnex.domain.market.OrderRepository;
import com.collabnex.domain.market.PhysicalProduct;
import com.collabnex.domain.market.PhysicalProductRepository;
import com.collabnex.domain.market.ServiceEnquiry;
import com.collabnex.domain.market.ServiceEnquiryRepository;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;
import com.collabnex.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepo;
	private final OrderItemRepository itemRepo;
	private final PhysicalProductRepository productRepo;
	private final UserRepository userRepo;
	final ServiceEnquiryRepository serviceEnquiryRepository;

	// ---------------------------------------------------------
	// 1) PLACE ORDER
	// ---------------------------------------------------------
	@Override
	public Order placeOrder(Long userId, OrderRequest request) {

		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		Order order = new Order();
		order.setUser(user);

		order.setFullName(request.getFullName());
		order.setPhoneNumber(request.getPhoneNumber());
		order.setAddressLine1(request.getAddressLine1());
		order.setAddressLine2(request.getAddressLine2());
		order.setLandmark(request.getLandmark());
		order.setPincode(request.getPincode());
		order.setCity(request.getCity());
		order.setState(request.getState());
		order.setCountry(request.getCountry());
		order.setCurrency(request.getCurrency());

		order.setCreatedAt(Instant.now());

		Order savedOrder = orderRepo.save(order);

		for (OrderItemRequest itemReq : request.getItems()) {

			PhysicalProduct product = productRepo.findById(itemReq.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));

			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(savedOrder);
			orderItem.setProduct(product);
			orderItem.setQuantity(itemReq.getQuantity());
			orderItem.setPrice(product.getPrice().doubleValue());

			itemRepo.save(orderItem);
		}

		return savedOrder;
	}

	// ---------------------------------------------------------
	// 2) GET MY ORDERS (Buyer-side)
	// ---------------------------------------------------------
	@Override
	public List<OrderResponse> getOrdersByUser(Long userId) {

		// Use JOIN FETCH to avoid lazy loading exceptions
		List<Order> orders = orderRepo.findAllOrdersWithItemsByUser(userId);

		return orders.stream().map(order -> {

			OrderResponse dto = new OrderResponse();
			dto.setOrderId(order.getId());
			dto.setFullName(order.getFullName());
			dto.setCity(order.getCity());
			dto.setCreatedAt(order.getCreatedAt());

			List<OrderItemResponse> itemDtos = order.getItems().stream().map(item -> {
				OrderItemResponse i = new OrderItemResponse();
				i.setItemId(item.getId());
				i.setProductId(item.getProduct().getId().longValue());
				i.setProductTitle(item.getProduct().getTitle());
				i.setPrice(item.getPrice());
				i.setQuantity(item.getQuantity());
				return i;
			}).toList();

			dto.setItems(itemDtos);

			return dto;
		}).toList();
	}

	// ---------------------------------------------------------
	// 3) GET ORDERS RECEIVED (Seller side)
	// ---------------------------------------------------------
	@Override
	public List<OrderItem> getOrdersReceived(Long sellerId) {
		return itemRepo.findReceivedOrders(sellerId); // custom JOIN FETCH repo method
	}

	@Override
	public AllOrdersResponse getAllOrders(Long userId) {

		// 1. Get physical orders list (DTOs)
		List<OrderResponse> physicalOrders = getOrdersByUser(userId);

		// 2. Get service enquiries sent by me
		List<ServiceEnquiry> enquiries = serviceEnquiryRepository.findBySenderId(userId);

		List<ServiceEnquiryResponse> enquiryDtos = enquiries.stream().map(e -> {
			ServiceEnquiryResponse dto = new ServiceEnquiryResponse();
			dto.setId(e.getId());
			dto.setServiceProductId(e.getServiceProduct().getId().longValue());

			dto.setServiceTitle(e.getServiceProduct().getTitle());
			dto.setBuyerName(e.getBuyerName());
			dto.setBuyerPhone(e.getBuyerPhone());
			dto.setMessage(e.getMessage());
			dto.setCreatedAt(e.getCreatedAt().toString());
			return dto;
		}).toList();

		// Wrap into final response
		AllOrdersResponse response = new AllOrdersResponse();
		response.setPhysicalOrders(physicalOrders);
		response.setServiceEnquiries(enquiryDtos);

		return response;
	}

}
