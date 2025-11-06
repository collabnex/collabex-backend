
package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.artwork.Artwork;
import com.collabnex.domain.user.User;
import com.collabnex.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
public class ArtworkMarketController {

    private final ArtworkService artworkService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<Artwork>>> list(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(artworkService.list(pageable)));
    }

    @PostMapping("/artworks")
    public ResponseEntity<ApiResponse<Artwork>> create(@AuthenticationPrincipal User user,
                                                       @RequestBody Artwork artwork) {
        artwork.setOwner(user);
        artwork.setListed(true);
        return ResponseEntity.ok(ApiResponse.ok(artworkService.create(artwork)));
    }
}
