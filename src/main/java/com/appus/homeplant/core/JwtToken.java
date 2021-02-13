package com.appus.homeplant.core;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class JwtToken {
    private final String id;
    private final String subject;
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiredAt;
    private final List<String> authorities;
}
