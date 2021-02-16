package com.appus.homeplant.users.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthenticationCode {

    @Id
    private String phoneNumber;
    private String code;
    private LocalDateTime createdDate = LocalDateTime.now();
    private Integer requestCount;
    private Boolean authenticationCheck;

    @Builder
    public AuthenticationCode(String phoneNumber,
                              String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.requestCount = 0;
        this.authenticationCheck = false;
    }

    public String getId() {
        return phoneNumber;
    }

    public void refresh(String code) {
        if (isExpiredDate()) {
            this.requestCount = 0;
        }

        increaseRequestCount();
        this.createdDate = LocalDateTime.now();
        this.code = code;
    }

    public void inspect(String code) {
        if (LocalDateTime.now().isAfter(createdDate.plusMinutes(3L))) {
            throw new IllegalArgumentException("인증번호 유효 시간이 지났습니다.");
        }

        if (!this.code.equals(code)) {
            throw new IllegalArgumentException("인증코드가 올바르지 않습니다.");
        }
    }

    public void authenticated() {
        this.authenticationCheck = true;
    }

    public boolean isChecked() {
        return this.authenticationCheck;
    }

    private boolean isExpiredDate() {
        return LocalDate.now().isAfter(this.createdDate.toLocalDate());
    }

    private void increaseRequestCount() {
        if (this.requestCount >= 5) {
            throw new RuntimeException("요청 제한 횟수를 초과하였습니다.");
        }
        this.requestCount++;
    }
}
