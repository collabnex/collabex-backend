package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.artwork.Artwork;
import com.collabnex.domain.user.User;
import com.collabnex.service.ArtworkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService service;

    @PostMapping
    public ResponseEntity<ApiResponse<Artwork>> create(@AuthenticationPrincipal User user, @Valid @RequestBody Artwork artwork) {
        artwork.setOwner(user);
        return ResponseEntity.ok(ApiResponse.ok(service.create(artwork)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Artwork>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.get(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Artwork>>> list(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(service.list(pageable)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
}
