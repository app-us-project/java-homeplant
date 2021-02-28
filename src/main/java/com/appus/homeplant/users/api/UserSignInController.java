package com.appus.homeplant.users.api;

import com.appus.homeplant.commons.jwt.JwtTokenProvider;
import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserSignInController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;

    //로그인 요청
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        Users member = usersRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 EMAIL 입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        List<String> roles = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return jwtTokenProvider.generateToken(member.getUsername(), roles);
    }
}
