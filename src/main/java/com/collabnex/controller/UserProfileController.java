package com.collabnex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> me(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.ok(service.getMyProfile(user)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> updateMyProfile(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, Object> body
    ) {
        UserProfile updated = service.updateMyProfile(user, body);
        return ResponseEntity.ok(ApiResponse.ok(updated));
    }

    
    @PutMapping("/me/domain")
    public ResponseEntity<ApiResponse<UserProfile>> updateDomain(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body
    ) {
        String domain = body.get("domain");

        if (!"user".equals(domain) && !"artist".equals(domain)) {
            throw new IllegalArgumentException("Invalid domain");
        }

        return ResponseEntity.ok(
                ApiResponse.ok(service.updateDomain(user, domain))
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchArtists(
            @RequestParam(name = "domain", required = false) String domain,
            @RequestParam(name = "city", required = false) String city
    ) {
        if (domain != null && city != null) {
            return ResponseEntity.ok(ApiResponse.ok(
                    service.findByDomainAndCity(domain, city)
            ));
        }

        if (domain != null) {
            return ResponseEntity.ok(ApiResponse.ok(
                    service.findByDomain(domain)
            ));
        }

        if (city != null) {
            return ResponseEntity.ok(ApiResponse.ok(
                    service.findByCity(city)
            ));
        }

        return ResponseEntity.ok(ApiResponse.ok(
                service.findAllArtists()
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getArtistProfile(
            @PathVariable("userId") Long userId
    ) {
        UserProfile profile = service.getPublicProfileByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(profile));
    }


    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<UserProfile>>> nearby(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "lat", required = false) Double lat,
            @RequestParam(name = "lng", required = false) Double lng
    ) {
        // If lat/lng not provided → fallback to my profile
        if (lat == null || lng == null) {
            UserProfile me = service.getMyProfile(user);

            if (me.getLatitude() == null || me.getLongitude() == null) {
                throw new IllegalArgumentException("Location not set for user");
            }

            lat = me.getLatitude();
            lng = me.getLongitude();
        }

        return ResponseEntity.ok(
                ApiResponse.ok(service.findNearbyArtists(lat, lng))
        );
    }



}
