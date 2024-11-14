package com.booking.api.Repo;


import com.booking.api.Dto.UserDto;
import com.booking.api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT EXISTS(SELECT 1 FROM user WHERE email = :email)", nativeQuery = true)
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findByUsername(String username);

}
