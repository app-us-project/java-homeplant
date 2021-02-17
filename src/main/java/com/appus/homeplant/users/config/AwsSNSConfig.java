package com.appus.homeplant.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsAsyncClient;

@Configuration
public class AwsSNSConfig {

    @Value("${aws.sns.access_key}")
    private String snsAccessKey;

    @Value("${aws.sns.secret_key}")
    private String snsSecretKey;

    @Bean
    public SnsAsyncClient snsAsyncClient() {
        return SnsAsyncClient.builder()
                .credentialsProvider(() -> AwsBasicCredentials.create(snsAccessKey, snsSecretKey))
                .region(Region.AP_NORTHEAST_1)
                .build();
    }
}
