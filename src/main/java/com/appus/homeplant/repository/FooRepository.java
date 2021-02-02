package com.appus.homeplant.repository;

import com.appus.homeplant.core.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FooRepository extends JpaRepository<Foo, Long> {
    Optional<Foo> findByUsername(String username);
}
