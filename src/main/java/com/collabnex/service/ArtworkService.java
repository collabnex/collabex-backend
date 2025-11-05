package com.collabnex.service;

import com.collabnex.domain.artwork.Artwork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtworkService {
    Artwork create(Artwork artwork);
    Artwork get(Long id);
    Page<Artwork> list(Pageable pageable);
    void delete(Long id);
}
