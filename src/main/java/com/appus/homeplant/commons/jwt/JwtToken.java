package com.appus.homeplant.commons.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class JwtToken {
    private final String subject;
    private final String id;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiredAt;
    private final List<String> authorities;
}
