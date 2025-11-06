
package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService service;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(service.getMyProfile(user)));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfile>> update(@AuthenticationPrincipal User user,
                                                           @RequestBody UserProfile payload) {
        return ResponseEntity.ok(ApiResponse.ok(service.updateMyProfile(user, payload)));
    }
}
