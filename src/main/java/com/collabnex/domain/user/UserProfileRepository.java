package com.collabnex.domain.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserId(Long userId);

    // 1️⃣ Filter by city
    List<UserProfile> findByCityIgnoreCase(String city);

    // 2️⃣ Filter by domain
    List<UserProfile> findByDomainIgnoreCase(String domain);

    // 3️⃣ Filter by domain + city
    List<UserProfile> findByDomainIgnoreCaseAndCityIgnoreCase(
            String domain,
            String city
    );

    // 4️⃣ All artists (bio exists & not user)
    @Query("""
        SELECT up FROM UserProfile up
        WHERE up.bio IS NOT NULL
        AND TRIM(up.bio) <> ''
        AND LOWER(up.domain) <> 'user'
    """)
    List<UserProfile> findAllArtists();

    // 5️⃣ Nearby artists (no distance returned)
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
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            Pageable pageable
    );

    // 6️⃣ Nearby artists by domain
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
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("domain") String domain,
            Pageable pageable
    );

    // 7️⃣ Nearby artists WITH distance (used by frontend)
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
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            Pageable pageable
    );
}
