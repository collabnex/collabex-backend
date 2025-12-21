
package com.collabnex.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.collabnex.common.exception.ResourceNotFoundException;
import com.collabnex.domain.user.User;
import com.collabnex.domain.user.UserProfile;
import com.collabnex.domain.user.UserProfileRepository;
import com.collabnex.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository profileRepo;
	private final ObjectMapper objectMapper;
	@Override
	@Transactional(readOnly = true)
	public UserProfile getMyProfile(User currentUser) {
		return profileRepo.findByUserId(currentUser.getId())
				.orElseGet(() -> UserProfile.builder().user(currentUser).fullName(currentUser.getEmail()).build());
	}

	@Override
	public UserProfile updateDomain(User user, String domain) {
		UserProfile profile = getMyProfile(user);
		profile.setDomain("user");
		return profileRepo.save(profile);
	}

	  @Override
	    public UserProfile updateMyProfile(User currentUser, Map<String, Object> body) {

	        UserProfile profile = profileRepo.findByUserId(currentUser.getId())
	                .orElseGet(() -> UserProfile.builder()
	                        .user(currentUser)
	                        .fullName(currentUser.getEmail())
	                        .domain("default")
	                        .visibility(true)
	                        .build()
	                );

	        /* -------- STRINGS -------- */
	        setString(body, "fullName", profile::setFullName);
	        setString(body, "domain", profile::setDomain);
	        setString(body, "bio", profile::setBio);
	        setString(body, "profession", profile::setProfession);
	        setString(body, "availabilityStatus", profile::setAvailabilityStatus);
	        setString(body, "locationText", profile::setLocationText);
	        setString(body, "country", profile::setCountry);
	        setString(body, "state", profile::setState);
	        setString(body, "city", profile::setCity);
	        setString(body, "portfolioUrl", profile::setPortfolioUrl);
	        setString(body, "profileImageUrl", profile::setProfileImageUrl);
	        setString(body, "collaborationType", profile::setCollaborationType);

	        /* -------- NUMBERS -------- */
	        setInteger(body, "yearsOfExperience", profile::setYearsOfExperience);
	        setDouble(body, "hourlyRate", profile::setHourlyRate);
	        setDouble(body, "latitude", profile::setLatitude);
	        setDouble(body, "longitude", profile::setLongitude);

	        /* -------- BOOLEAN -------- */
	        setBoolean(body, "visibility", profile::setVisibility);

	        /* -------- JSON -------- */
	        setJson(body, "skills", profile::setSkills);
	        setJson(body, "tags", profile::setTags);
	        setJson(body, "socialLinks", profile::setSocialLinks);

	        return profileRepo.save(profile);
	    }

	    /* ================= HELPERS ================= */

	    private void setString(Map<String, Object> body, String key, Consumer<String> setter) {
	        Object value = body.get(key);
	        if (value != null) {
	            setter.accept(value.toString());
	        }
	    }

	    private void setInteger(Map<String, Object> body, String key, Consumer<Integer> setter) {
	        Object value = body.get(key);
	        if (value instanceof Number number) {
	            setter.accept(number.intValue());
	        }
	    }

	    private void setDouble(Map<String, Object> body, String key, Consumer<Double> setter) {
	        Object value = body.get(key);
	        if (value instanceof Number number) {
	            setter.accept(number.doubleValue());
	        }
	    }

	    private void setBoolean(Map<String, Object> body, String key, Consumer<Boolean> setter) {
	        Object value = body.get(key);
	        if (value instanceof Boolean bool) {
	            setter.accept(bool);
	        }
	    }

	    private void setJson(Map<String, Object> body, String key, Consumer<String> setter) {
	        Object value = body.get(key);
	        if (value != null) {
	            try {
	                setter.accept(objectMapper.writeValueAsString(value));
	            } catch (Exception e) {
	                throw new RuntimeException("Invalid JSON field: " + key, e);
	            }
	        }
	    }
	
	
	
	  @Override
	    public List<UserProfile> findByCity(String city) {
	        return profileRepo.findByCityIgnoreCase(city);
	    }

	    @Override
	    public List<UserProfile> findByDomain(String domain) {
	        return profileRepo.findByDomainIgnoreCase(domain);
	    }

	    @Override
	    public List<UserProfile> findByDomainAndCity(String domain, String city) {
	        return profileRepo.findByDomainIgnoreCaseAndCityIgnoreCase(domain, city);
	    }
	
	    @Override
	    public UserProfile getPublicProfileByUserId(Long userId) {
	        return profileRepo.findByUserId(userId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException(
	                                "Artist profile not found for userId: " + userId
	                        )
	                );
	    }
	    @Override
	    public List<UserProfile> findAllArtists() {
	        return profileRepo.findAllArtists();
	    }




	    @Override
	    public List<UserProfile> findNearbyArtistsByDomain(
	            Double lat,
	            Double lng,
	            String domain
	    ) {
	        return profileRepo.findNearbyArtistsByDomain(
	                lat,
	                lng,
	                domain,
	                PageRequest.of(0, 10)
	        );
	    }
	    
	    @Override
	    public List<UserProfile> findNearbyArtists(Double lat, Double lng) {

	        List<Object[]> rows =
	                profileRepo.findNearbyArtistsWithDistance(
	                        lat,
	                        lng,
	                        PageRequest.of(0, 10) // ✅ this IS Pageable
	                );

	        return rows.stream().map(row -> {
	            UserProfile profile = (UserProfile) row[0];
	            Double distance = (Double) row[1];

	            profile.setDistanceKm(
	                    Math.round(distance * 10.0) / 10.0
	            );

	            return profile;
	        }).toList();
	    }


}