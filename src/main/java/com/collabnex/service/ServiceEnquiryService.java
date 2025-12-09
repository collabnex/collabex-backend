package com.collabnex.service;

import com.collabnex.domain.market.ServiceEnquiry;
import java.util.List;

public interface ServiceEnquiryService {

	ServiceEnquiry sendEnquiry(Long senderUserId, Long serviceProductId, 
            String name, String phone, String message);


    List<ServiceEnquiry> getMyEnquiries(Long senderId);

    List<ServiceEnquiry> getReceivedEnquiries(Long sellerId);
}
