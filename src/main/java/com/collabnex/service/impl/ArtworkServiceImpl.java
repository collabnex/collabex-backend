package com.collabnex.service.impl;

import com.collabnex.common.exception.NotFoundException;
import com.collabnex.domain.artwork.Artwork;
import com.collabnex.domain.artwork.ArtworkRepository;
import com.collabnex.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository repository;

    @Override
    public Artwork create(Artwork artwork) {
        return repository.save(artwork);
    }

    @Override
    public Artwork get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Artwork not found"));
    }

    @Override
    public Page<Artwork> list(Pageable pageable) {
        return repository.findByListedTrue(pageable);
    }

    @Override
    public void delete(Long id) {
        var a = get(id);
        repository.delete(a);
    }
}
