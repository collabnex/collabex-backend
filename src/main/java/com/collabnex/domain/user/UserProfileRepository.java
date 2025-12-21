package com.collabnex.domain.user;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("""
            SELECT up FROM UserProfile up
            WHERE up.bio IS NOT NULL
            AND TRIM(up.bio) <> ''
            AND up.domain <> 'user'
        """)
        List<UserProfile> findAllArtists();
    
    
    @Query("""
            SELECT up FROM UserProfile up
            WHERE up.latitude IS NOT NULL
            AND up.longitude IS NOT NULL
            AND up.bio IS NOT NULL
            AND TRIM(up.bio) <> ''
            ORDER BY (
                6371 * acos(
                    cos(radians(:lat)) *
                    cos(radians(up.latitude)) *
                    cos(radians(up.longitude) - radians(:lng)) +
                    sin(radians(:lat)) *
                    sin(radians(up.latitude))
                )
            )
        """)
        List<UserProfile> findNearbyArtists(
                Double lat,
                Double lng,
                org.springframework.data.domain.Pageable pageable
        );

        // ✅ Nearby artists by domain
        @Query("""
            SELECT up FROM UserProfile up
            WHERE up.latitude IS NOT NULL
            AND up.longitude IS NOT NULL
            AND LOWER(up.domain) = LOWER(:domain)
            AND up.bio IS NOT NULL
            AND TRIM(up.bio) <> ''
            ORDER BY (
                6371 * acos(
                    cos(radians(:lat)) *
                    cos(radians(up.latitude)) *
                    cos(radians(up.longitude) - radians(:lng)) +
                    sin(radians(:lat)) *
                    sin(radians(up.latitude))
                )
            )
        """)
        List<UserProfile> findNearbyArtistsByDomain(
                Double lat,
                Double lng,
                String domain,
                org.springframework.data.domain.Pageable pageable
        );
        
        @Query("""
        	    SELECT up,
        	    (
        	        6371 * acos(
        	            cos(radians(:lat)) *
        	            cos(radians(up.latitude)) *
        	            cos(radians(up.longitude) - radians(:lng)) +
        	            sin(radians(:lat)) *
        	            sin(radians(up.latitude))
        	        )
        	    )
        	    FROM UserProfile up
        	    WHERE up.latitude IS NOT NULL
        	    AND up.longitude IS NOT NULL
        	    AND up.bio IS NOT NULL
        	    AND TRIM(up.bio) <> ''
        	    ORDER BY 2
        	""")
        	List<Object[]> findNearbyArtistsWithDistance(
        	        Double lat,
        	        Double lng,
        	        Pageable pageable
        	);


}