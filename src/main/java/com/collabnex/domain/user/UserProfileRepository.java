package com.collabnex.domain.user;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
    // 1️⃣ filter by city
    List<UserProfile> findByCityIgnoreCase(String city);

    // 2️⃣ filter by domain (artist category)
    List<UserProfile> findByDomainIgnoreCase(String domain);

    // 3️⃣ filter by domain + city
    List<UserProfile> findByDomainIgnoreCaseAndCityIgnoreCase(
            String domain,
            String city
    );

}