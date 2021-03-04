package com.appus.homeplant.users.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class ChangePasswordDto {

    @Email
    private String email;

    private String password;

    private String passwordCheck;

    @Pattern(regexp = "010[0-9]{7,8}")
    private String phoneNumber;

    @JsonIgnore
    public boolean isEqualsTwoPassword() {
        return password.equals(passwordCheck);
    }
}
