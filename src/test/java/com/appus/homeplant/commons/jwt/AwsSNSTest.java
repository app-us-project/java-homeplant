package com.appus.homeplant.commons.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.sns.SnsAsyncClient;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class AwsSNSTest {

    @Autowired
    private SnsAsyncClient snsAsyncClient;

    @Test
    void sendTest() {
        CompletableFuture<PublishResponse> hello = snsAsyncClient.publish(
                builder -> builder.phoneNumber("+821038998057")
                        .message("hello world").subject("homeplant")
                        .build()
        );

        hello.join();
    }

}
