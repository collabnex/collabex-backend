package com.collabnex.domain.market;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceProductRepository extends JpaRepository<ServiceProduct, Long> {
    List<ServiceProduct> findByUserId(Integer userId);
}
