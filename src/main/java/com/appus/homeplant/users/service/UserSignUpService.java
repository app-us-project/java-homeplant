package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.*;
import com.appus.homeplant.users.repository.AuthoritiesRepository;
import com.appus.homeplant.users.repository.TermsRepository;
import com.appus.homeplant.users.repository.UserTermsRepository;
import com.appus.homeplant.users.repository.UsersRepository;
import com.appus.homeplant.users.service.dto.TermsDTO;
import com.appus.homeplant.users.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Service
public class UserSignUpService {

    private final UsersRepository usersRepository;
    private final TermsRepository termsRepository;
    private final UserTermsRepository userTermsRepository;
    private final AuthoritiesRepository authoritiesRepository;

    private final AuthenticationCodeSendService authenticationCodeSendService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUpUsers(UserDTO userDTO) {
        authenticationCodeSendService.authenticationCheck(userDTO.getPhoneNumber());

        if (!userDTO.isEqualsTwoPassword()) {
            throw new IllegalArgumentException("입력된 패스워드가 일치하지 않습니다.");
        }

        Users createdUsers = createUsers(userDTO);
        List<UserTerms> contractUserTerms = contractUserTerms(createdUsers, userDTO.getTerms());

        usersRepository.save(createdUsers);
        userTermsRepository.saveAll(contractUserTerms);
    }

    private Users createUsers(UserDTO userDTO) {
        Users createdUsers = Users.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPasswordCheck()))
                .phoneNumber(userDTO.getPhoneNumber())
                .userStatus(Users.UserStatus.ENABLED)
                .authorities(emptyList())
                .build();

        List<Authorities> defaultAuthorities = authoritiesRepository
                .findAllByRoleIn(singletonList(Authorities.Role.ROLE_USER));

        List<UserAuthorities> userAuthorities = defaultAuthorities.stream()
                .map(a -> UserAuthorities.builder().authorities(a).build())
                .collect(toList());

        createdUsers.addAuthorities(userAuthorities);
        return createdUsers;
    }

    private List<UserTerms> contractUserTerms(Users users, Set<TermsDTO> termsDTOs) {
        List<Terms> terms = termsRepository.findAllByTermsType(Terms.TermsType.SIGN_UP);

        Map<Long, TermsDTO> userTermsMap = termsDTOs.stream()
                .collect(toMap(TermsDTO::getTermsId, x -> x));

        return terms.stream()
                .map(x -> {
                    if (!userTermsMap.containsKey(x.getId())) {
                        throw new IllegalArgumentException("회원 가입 약정이 누락되었습니다.");
                    }

                    TermsDTO termsDTO = userTermsMap.get(x.getId());
                    if (x.getRequired() && !termsDTO.getAgreement()) {
                        throw new IllegalArgumentException("필수 약정이 동의되지 않았습니다.");
                    }

                    UserTerms userTerms = new UserTerms(users);
                    userTerms.agreeTerms(x, termsDTO.getAgreement());
                    return userTerms;
                }).collect(toList());
    }
}
