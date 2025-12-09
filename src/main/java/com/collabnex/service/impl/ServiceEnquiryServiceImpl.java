package com.collabnex.service.impl;

import com.collabnex.domain.market.ServiceEnquiry;
import com.collabnex.domain.market.ServiceEnquiryRepository;
import com.collabnex.domain.market.ServiceProduct;
import com.collabnex.domain.market.ServiceProductRepository;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;
import com.collabnex.service.ServiceEnquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEnquiryServiceImpl implements ServiceEnquiryService {

    private final ServiceEnquiryRepository repo;
    private final ServiceProductRepository productRepo;
    private final UserRepository userRepo;

    @Override
    public ServiceEnquiry sendEnquiry(Long senderUserId, Long serviceProductId,
                                      String name, String phone, String message) {

        User sender = userRepo.findById(senderUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceProduct product = productRepo.findById(serviceProductId)
                .orElseThrow(() -> new RuntimeException("Service product not found"));

        ServiceEnquiry enquiry = new ServiceEnquiry();
        enquiry.setSender(sender);
        enquiry.setServiceProduct(product);
        enquiry.setBuyerName(name);
        enquiry.setBuyerPhone(phone);
        enquiry.setMessage(message);

        return repo.save(enquiry);
    }


    @Override
    public List<ServiceEnquiry> getMyEnquiries(Long senderId) {
        return repo.findBySenderId(senderId);
    }

    @Override
    public List<ServiceEnquiry> getReceivedEnquiries(Long sellerId) {
        return repo.findByServiceProductUserId(sellerId);
    }


	@Override
	public List<ServiceEnquiry> getMyServiceEnquiries(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
