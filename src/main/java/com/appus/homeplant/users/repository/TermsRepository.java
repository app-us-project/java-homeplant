package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.Terms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermsRepository extends JpaRepository<Terms, Long> {
    List<Terms> findAllByTermsType(Terms.TermsType termsType);
}
