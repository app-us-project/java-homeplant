package com.appus.homeplant.users.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UserDto {

    @Email
    private String email;

    private String password;

    private String passwordCheck;

    @Pattern(regexp = "010[0-9]{7,8}")
    private String phoneNumber;

    private Set<TermsAgreementDto> terms;

    @JsonIgnore
    public boolean isEqualsTwoPassword() {
        return password.equals(passwordCheck);
    }
}
