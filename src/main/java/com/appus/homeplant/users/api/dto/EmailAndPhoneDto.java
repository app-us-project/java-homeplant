package com.appus.homeplant.users.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailAndPhoneDto {

    @Email
    private String email;

    @Pattern(regexp = "010[0-9]{7,8}")
    private String phone;
}
