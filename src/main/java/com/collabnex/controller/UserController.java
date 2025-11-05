package com.collabnex.controller;

import com.collabnex.common.dto.ApiResponse;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.domain.user.UserProfileRepository;
import com.collabnex.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserProfileRepository profileRepository;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.ok(user));
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ApiResponse<UserProfile>> myProfile(@AuthenticationPrincipal User user) {
        var p = profileRepository.findByUserId(user.getId()).orElse(null);
        return ResponseEntity.ok(ApiResponse.ok(p));
    }
}
