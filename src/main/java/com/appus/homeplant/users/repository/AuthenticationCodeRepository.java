package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.AuthenticationCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AuthenticationCodeRepository extends CrudRepository<AuthenticationCode, String> {
}
