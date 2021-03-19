package com.appus.homeplant.users.service;

import com.appus.homeplant.users.api.dto.SignInRequest;
import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UserRepository;
import com.appus.homeplant.users.service.dto.ChangePasswordDto;
import com.appus.homeplant.users.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSignInService userSignInService;


    @Transactional
    public Long updateMypage(UserDto userDto) {
        Users users=userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("사용자 이메일이 존재하지 않습니다."));

        users.changePassword(passwordEncoder.encode(userDto.getPassword()));
        return users.getId();
    }
}
