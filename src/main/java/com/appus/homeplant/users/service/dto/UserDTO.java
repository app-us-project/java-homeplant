package com.appus.homeplant.users.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@NoArgsConstructor
public class UserDTO {

    @Email
    private String email;

    private String password;

    private String passwordCheck;

    private String phoneNumber;

    private Set<TermsDTO> terms;

    public boolean isEqualsTwoPassword() {
        return password.equals(passwordCheck);
    }
}
