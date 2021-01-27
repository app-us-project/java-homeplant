package com.appus.homeplant.repository;

import com.appus.homeplant.core.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
