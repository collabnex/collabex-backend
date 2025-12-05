package com.collabnex.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.collabnex.domain.market.ServiceProduct;
import com.collabnex.domain.market.ServiceProductRepository;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceProductServiceImpl implements ServiceProductService {

    private final ServiceProductRepository repo;
    private final UserRepository userRepo;

    @Override
    public ServiceProduct addServiceProduct(ServiceProduct p, Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        p.setUser(user);
        return repo.save(p);
    }

    @Override
    public List<ServiceProduct> getAll() {
        return repo.findAll();
    }
}

