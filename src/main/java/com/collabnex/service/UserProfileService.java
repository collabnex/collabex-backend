
package com.collabnex.service;

import java.util.List;

import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;

public interface UserProfileService {
    UserProfile getMyProfile(User currentUser);
    UserProfile updateMyProfile(User currentUser, UserProfile payload);
    List<UserProfile> findByCity(String city);

    List<UserProfile> findByDomain(String domain);
    UserProfile getPublicProfileByUserId(Long profileId);
    List<UserProfile> findByDomainAndCity(String domain, String city);
    public UserProfile updateDomain(User user, String domain);
    
    
}
