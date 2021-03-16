package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.Users;
import com.appus.homeplant.users.repository.UsersRepository;
import com.appus.homeplant.users.service.dto.ChangePasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PasswordSearchService {

    private final UsersRepository usersRepository;
    private final AuthenticationCodeService authenticationCodeService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void sendAuthenticationNumber(String email, String phone) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입된 이력이 없는 Email 입니다."));

        if (!users.isSamePhoneNumber(phone)) {
            throw new IllegalArgumentException("해당 계정의 이메일이 입력된 휴대폰 번호와 일치하지 않습니다.");
        }

        authenticationCodeService.sendAuthenticationNumber(phone);
    }

    public void authenticateCode(String phone, String code) {
        authenticationCodeService.authenticateCode(phone, code);
    }

    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        if (!changePasswordDto.isEqualsTwoPassword()) {
            throw new IllegalArgumentException("입력된 두 패스워드가 일치하지 않습니다.");
        }

        // 휴대폰 인증 여부 임시 주석
        //authenticationCodeService.authenticationCheck(changePasswordDto.getPhoneNumber());
        Users users = usersRepository.findByEmail(changePasswordDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자 이메일이 존재하지 않습니다."));

        users.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        authenticationCodeService.deleteAuthenticationCode(changePasswordDto.getPhoneNumber());
    }
}
