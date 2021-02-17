package com.appus.homeplant.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class SMSMessageSender {

    private final SnsAsyncClient snsAsyncClient;

    public CompletableFuture<PublishResponse> publish(String subject, String phoneNumber, String message) {
        return snsAsyncClient.publish(PublishRequest.builder()
                .subject(subject)
                .phoneNumber(convertCountryCode(phoneNumber))
                .message(message)
                .build());
    }

    private String convertCountryCode(String phoneNumber) {
        return "+82" + phoneNumber.substring(1);
    }
}
