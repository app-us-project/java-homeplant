package com.appus.homeplant.service;

import com.appus.homeplant.core.Foo;
import com.appus.homeplant.repository.FooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final FooRepository fooRepository;
    private final HttpSession httpSession;

    //로그인 인증
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Foo> optionalFoo=fooRepository.findByUsername(username);
        if(optionalFoo.isPresent()) {
            Foo item= optionalFoo.get();

        }


        return userDetails;
    }
}
