package com.appus.homeplant.users.service;

import com.appus.homeplant.commons.jwt.JwtTokenProvider;
import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserSignInService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public String authenticateAndIssueJWT(String email, String password) {
        Users member = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 EMAIL 입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return jwtTokenProvider.generateToken(member.getUsername(), roles);
    }
}
