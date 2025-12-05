package com.collabnex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.collabnex.domain.market.PhysicalProduct;
import com.collabnex.domain.market.PhysicalProductRepository;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhysicalProductServiceImpl implements PhysicalProductService {

    private final PhysicalProductRepository repo;
    private final UserRepository userRepo;

    @Override
    public PhysicalProduct addPhysicalProduct(PhysicalProduct p, Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        p.setUser(user);
        return repo.save(p);
    }

    @Override
    public List<PhysicalProduct> getAll() {
        return repo.findAll();
    }
}
