package com.collabnex.domain.artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    Page<Artwork> findByListedTrue(Pageable pageable);
}