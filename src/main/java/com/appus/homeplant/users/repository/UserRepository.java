package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String username);
    Boolean existsByPhoneNumber(String phone);
}
