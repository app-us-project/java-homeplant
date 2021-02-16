package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.AuthenticationCode;
import com.appus.homeplant.users.repository.AuthenticationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Component
class AuthenticationCodeSendService {

    private final AuthenticationCodeRepository authenticationCodeRepository;
    private final SnsAsyncClient snsAsyncClient;

    private static final String SUBJECT = "homeplant";
    private static final String MESSAGE_FORMAT = "인증번호 안내드립니다. \n인증번호 : %s";

    public void sendVerificationNumber(String phoneNumber) {
        AuthenticationCode publishedCode = publishNewAuthenticationCode(phoneNumber);

        Future<PublishResponse> publishedResponse = publish(
                publishedCode.getPhoneNumber(),
                publishedCode.getCode());

        try {
            publishedResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        authenticationCodeRepository.save(publishedCode);
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

    private AuthenticationCode publishNewAuthenticationCode(String phoneNumber) {
        String randomCode = randomCode();
        AuthenticationCode publishedCode = authenticationCodeRepository.findById(phoneNumber)
                .orElseGet(() -> AuthenticationCode.builder()
                        .phoneNumber(phoneNumber)
                        .build()
                );

        publishedCode.refresh(randomCode);
        return publishedCode;
    }

    private String randomCode() {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        return Integer.toString(current.nextInt(100_000, 1_000_000));
    }

    private Future<PublishResponse> publish(String phoneNumber, String code) {
        String countryCodePhoneNumber = convertCountryCode(phoneNumber);

        return snsAsyncClient.publish(
                publishBuilder -> publishBuilder
                        .subject(SUBJECT)
                        .phoneNumber(countryCodePhoneNumber)
                        .message(getMessage(code))
        );
    }

    private String convertCountryCode(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }

    private String getMessage(String code) {
        return String.format(MESSAGE_FORMAT, code);
    }
}
