package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
