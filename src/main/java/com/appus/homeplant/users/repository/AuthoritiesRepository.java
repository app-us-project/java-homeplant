package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    List<Authorities> findAllByRoleIn(List<Authorities.Role> role);
}
