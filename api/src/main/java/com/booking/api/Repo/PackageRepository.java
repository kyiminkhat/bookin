package com.booking.api.Repo;

import com.booking.api.Entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    @Query(value = "SELECT * FROM package p WHERE p.country = :country", nativeQuery = true)
    List<Package> findByCountry(String country);
}
