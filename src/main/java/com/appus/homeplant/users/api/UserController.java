package com.appus.homeplant.users.api;

import com.appus.homeplant.commons.jwt.JwtTokenProvider;
import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    private final AuthenticationManager authenticationManager;

    //로그인 요청
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get("email"), user.get("password")));
        String email = (String) authenticate.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authenticate.getAuthorities();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return jwtTokenProvider.generateToken(email, roles);
    }
}
