package com.collabnex.domain.market;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalProductRepository extends JpaRepository<PhysicalProduct, Integer> {
    List<PhysicalProduct> findByUserId(Integer userId);
}

