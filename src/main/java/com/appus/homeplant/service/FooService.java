package com.appus.homeplant.service;

import com.appus.homeplant.core.Foo;
import com.appus.homeplant.repository.FooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FooService {

    private final FooRepository fooRepository;

    @Transactional
    public void t() {
        Foo foo = new Foo();

        fooRepository.save(new Foo());
    }
}
