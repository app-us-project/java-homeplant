//package com.appus.homeplant.service;
//
//import com.appus.homeplant.core.Foo;
//import com.appus.homeplant.repository.JwtTokenRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class JwtTokenService implements UserDetailsService {
//
//    private final JwtTokenRepository jwtTokenRepository;
//
//    //로그인 인증
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Foo> optionalFoo= jwtTokenRepository.findByUsername(username);
//        if(optionalFoo.isPresent()) {
//            Foo item= optionalFoo.get();
//////////////////////////////////////////////
//        }
//
//
//        return userDetails;
//    }
//}
