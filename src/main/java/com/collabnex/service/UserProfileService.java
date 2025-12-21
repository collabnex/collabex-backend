
package com.collabnex.service;

import java.util.List;
import java.util.Map;

import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;

public interface UserProfileService {
    UserProfile getMyProfile(User currentUser);
    public UserProfile updateMyProfile(User currentUser, Map<String, Object> body);
    List<UserProfile> findByCity(String city);
    List<UserProfile> findAllArtists();
    List<UserProfile> findByDomain(String domain);
    UserProfile getPublicProfileByUserId(Long profileId);
    List<UserProfile> findByDomainAndCity(String domain, String city);
    public UserProfile updateDomain(User user, String domain);
    List<UserProfile> findNearbyArtists(Double lat, Double lng);

    List<UserProfile> findNearbyArtistsByDomain(
            Double lat,
            Double lng,
            String domain
    );

    
}
