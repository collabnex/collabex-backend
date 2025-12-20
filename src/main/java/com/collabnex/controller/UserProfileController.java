package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<ApiResponse<UserProfile>> update(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, Object> body
    ) {
        try {
            UserProfile payload = new UserProfile();

            payload.setBio((String) body.get("bio"));
            payload.setCity((String) body.get("city"));
            payload.setProfession((String) body.get("profession"));

            // ✅ KEY FIX: convert array → JSON string
            if (body.get("skills") != null) {
                payload.setSkills(objectMapper.writeValueAsString(body.get("skills")));
            }

            return ResponseEntity.ok(
                    ApiResponse.ok(service.updateMyProfile(user, payload))
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid profile payload", e);
        }
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


}
