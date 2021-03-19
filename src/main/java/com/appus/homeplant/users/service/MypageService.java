package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UserRepository;
import com.appus.homeplant.users.service.dto.ChangePasswordDto;
import com.appus.homeplant.users.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSignInService userSignInService;


    @Transactional
    public Long updateMypage(UserDto userDto, Long id) {
        Users users=userRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("사용자 이메일이 존재하지 않습니다."));

        users.updateMypage(userDto);
        return id;
    }

    public Long updatePassword(UserDto userDto, ChangePasswordDto changePasswordDto) throws Exception {
        Users users=userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("사용자 이메일이 존재하지 않습니다."));

        if (!changePasswordDto.isEqualsTwoPassword()) {
            throw new IllegalArgumentException("입력된 두 패스워드가 일치하지 않습니다.");
        } else {
            users.changePassword(passwordEncoder.encode(userDto.getPassword()));
            return users.getId();
        }
    }
}
