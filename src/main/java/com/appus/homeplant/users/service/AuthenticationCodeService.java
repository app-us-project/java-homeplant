package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.AuthenticationCode;
import com.appus.homeplant.users.repository.AuthenticationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class AuthenticationCodeService {

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final SMSMessageSender smsMessageSender;

    private static final String SUBJECT = "homeplant";
    private static final String MESSAGE_FORMAT = "인증번호 안내드립니다. \n인증번호 : %s";

    public void sendAuthenticationNumber(String phoneNumber) {
        AuthenticationCode createdCode = createAuthenticationCode(phoneNumber);
        Future<PublishResponse> publishedResponse = smsMessageSender.publish(
                SUBJECT,
                createdCode.getPhoneNumber(),
                String.format(MESSAGE_FORMAT, createdCode.getCode())
        );

        try {
            publishedResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        authenticationCodeRepository.save(createdCode);
    }

    public void authenticateCode(String phoneNumber, String code) {
        AuthenticationCode authenticationCode = authenticationCodeRepository.findById(phoneNumber)
                .orElseThrow(IllegalArgumentException::new);

        authenticationCode.inspect(code);
        authenticationCode.authenticated();
    }

    public void authenticationCheck(String phoneNumber) {
        AuthenticationCode authenticationCode = authenticationCodeRepository.findById(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("휴대폰 번호가 인증되지 않았습니다."));

        if (!authenticationCode.isChecked()) {
            throw new IllegalArgumentException("휴대폰 번호가 인증되지 않았습니다.");
        }
    }

    public void deleteAuthenticationCode(String phoneNumber) {
        authenticationCodeRepository.deleteById(phoneNumber);
    }

    private AuthenticationCode createAuthenticationCode(String phoneNumber) {
        AuthenticationCode publishedCode = authenticationCodeRepository.findById(phoneNumber)
                .orElseGet(() -> AuthenticationCode.builder()
                        .phoneNumber(phoneNumber)
                        .build()
                );

        publishedCode.refresh();
        return publishedCode;
    }
}
