package com.appus.homeplant.users.repository.impl;

import com.appus.homeplant.users.core.AuthenticationCode;
import com.appus.homeplant.users.repository.AuthenticationCodeRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Repository
class AuthenticationCodeRepositoryImpl implements AuthenticationCodeRepository {

    private final Map<String, AuthenticationCode> verificationCodeStorage;
    private final static int MAX_SIZE = 1000;

    public AuthenticationCodeRepositoryImpl() {
        verificationCodeStorage = new LinkedHashMap<String, AuthenticationCode>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_SIZE;
            }
        };
    }

    @Override
    public <S extends AuthenticationCode> S save(S entity) {
        verificationCodeStorage.remove(entity.getId());
        verificationCodeStorage.put(entity.getPhoneNumber(), entity);
        return entity;
    }

    @Override
    public <S extends AuthenticationCode> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public Optional<AuthenticationCode> findById(String s) {
        return Optional.ofNullable(verificationCodeStorage.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return verificationCodeStorage.containsKey(s);
    }

    @Override
    public Iterable<AuthenticationCode> findAll() {
        return verificationCodeStorage.values();
    }

    @Override
    public Iterable<AuthenticationCode> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return verificationCodeStorage.size();
    }

    @Override
    public void deleteById(String s) {
        verificationCodeStorage.remove(s);
    }

    @Override
    public void delete(AuthenticationCode entity) {
        verificationCodeStorage.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends AuthenticationCode> entities) {
        for (AuthenticationCode entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        verificationCodeStorage.clear();
    }
}
