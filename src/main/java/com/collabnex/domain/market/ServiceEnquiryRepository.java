package com.collabnex.domain.market;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceEnquiryRepository extends JpaRepository<ServiceEnquiry, Long> {

    List<ServiceEnquiry> findByServiceProductUserId(Long userId); // enquiries received

    List<ServiceEnquiry> findBySenderId(Long userId); // enquiries I sent
}
