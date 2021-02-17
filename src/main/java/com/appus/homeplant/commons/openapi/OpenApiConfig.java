package com.appus.homeplant.commons.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(getInfo());
    }

    @Bean
    public GroupedOpenApi usersOpenApi() {
        String[] paths = {"/users/**"};
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi paymentsOpenApi() {
        String[] paths = {"/payments/**"};
        return GroupedOpenApi.builder()
                .group("payments")
                .pathsToMatch(paths)
                .build();
    }

    private Info getInfo() {
        Info info = new Info();
        info.title("HomePlant API");
        info.description("회원 API, 결제 API를 정의한다.");
        info.version("v1");

        return info;
    }
}
