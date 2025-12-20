
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
				.orElseGet(() -> UserProfile.builder().user(currentUser).fullName(currentUser.getEmail()).build());
	}

	@Override
	public UserProfile updateDomain(User user, String domain) {
		UserProfile profile = getMyProfile(user);
		profile.setDomain("user");
		return profileRepo.save(profile);
	}

	@Override
	@Transactional
	public UserProfile updateMyProfile(User currentUser, UserProfile payload) {
		// 1️⃣ Fetch existing profile or create new if not found
		UserProfile profile = profileRepo.findByUserId(currentUser.getId())
				.orElseGet(() -> UserProfile.builder().user(currentUser).fullName(currentUser.getEmail()) // fallback
						.build());

		// 2️⃣ Safe updates — update only if payload has value (prevents null overwrite)
		if (payload.getFullName() != null)
			profile.setFullName(payload.getFullName());
		if (payload.getBio() != null)
			profile.setBio(payload.getBio());
		if (payload.getSkills() != null)
			profile.setSkills(payload.getSkills());
		if (payload.getProfileImageUrl() != null)
			profile.setProfileImageUrl(payload.getProfileImageUrl());
		if (payload.getLocationText() != null)
			profile.setLocationText(payload.getLocationText());
		if (payload.getPortfolioUrl() != null)
			profile.setPortfolioUrl(payload.getPortfolioUrl());

		// 3️⃣ New Extended Fields (for search & filtering)
		if (payload.getProfession() != null)
			profile.setProfession(payload.getProfession());
		if (payload.getAvailabilityStatus() != null)
			profile.setAvailabilityStatus(payload.getAvailabilityStatus());
		if (payload.getYearsOfExperience() != null)
			profile.setYearsOfExperience(payload.getYearsOfExperience());
		if (payload.getHourlyRate() != null)
			profile.setHourlyRate(payload.getHourlyRate());
		if (payload.getTags() != null)
			profile.setTags(payload.getTags());
		if (payload.getCountry() != null)
			profile.setCountry(payload.getCountry());
		if (payload.getState() != null)
			profile.setState(payload.getState());
		if (payload.getCity() != null)
			profile.setCity(payload.getCity());
		if (payload.getLatitude() != null)
			profile.setLatitude(payload.getLatitude());
		if (payload.getLongitude() != null)
			profile.setLongitude(payload.getLongitude());
		if (payload.getCollaborationType() != null)
			profile.setCollaborationType(payload.getCollaborationType());
		if (payload.getSocialLinks() != null)
			profile.setSocialLinks(payload.getSocialLinks());
		if (payload.getVisibility() != null)
			profile.setVisibility(payload.getVisibility());
		if (payload.getDomain() != null)
			profile.setDomain(payload.getDomain());
		// 4️⃣ Save changes
		UserProfile updated = profileRepo.save(profile);

		// 5️⃣ Optional debug log for verification (useful in dev)
		System.out.println("[UserProfileService] Updated profile for userId=" + currentUser.getId() + " → "
				+ updated.getFullName());

		return updated;
	}

}