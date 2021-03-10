package com.appus.homeplant.users.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor
public class SignInRequest {

    @Email
    private String email;

    private String password;
}
