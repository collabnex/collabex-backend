
package com.collabnex.service.impl;

import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.domain.user.UserProfileRepository;
import com.collabnex.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository profileRepo;

    @Override
    @Transactional(readOnly = true)
    public UserProfile getMyProfile(User currentUser) {
        return profileRepo.findByUserId(currentUser.getId())
                .orElseGet(() -> UserProfile.builder()
                        .user(currentUser)
                        .fullName(currentUser.getEmail())
                        .build());
    }

    @Override
    @Transactional
    public UserProfile updateMyProfile(User currentUser, UserProfile payload) {
        var profile = profileRepo.findByUserId(currentUser.getId())
                .orElseGet(() -> UserProfile.builder().user(currentUser).build());
        profile.setFullName(payload.getFullName());
        profile.setBio(payload.getBio());
        profile.setSkills(payload.getSkills());
        profile.setProfileImageUrl(payload.getProfileImageUrl());
        profile.setLocationText(payload.getLocationText());
        profile.setPortfolioUrl(payload.getPortfolioUrl());
        return profileRepo.save(profile);
    }
}
