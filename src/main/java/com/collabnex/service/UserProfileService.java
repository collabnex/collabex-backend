
package com.collabnex.service;

import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;

public interface UserProfileService {
    UserProfile getMyProfile(User currentUser);
    UserProfile updateMyProfile(User currentUser, UserProfile payload);
    
    public UserProfile updateDomain(User user, String domain);
}
