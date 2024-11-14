package com.booking.api.Repo;


import com.booking.api.Entity.UserPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {
    @Query(value = "SELECT * FROM user_package WHERE user_id = :userId AND package_id = :packageId", nativeQuery = true)
    Optional<UserPackage> findByUserIdAndPackageId(Long userId, Long packageId);
}