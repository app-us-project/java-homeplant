package com.appus.homeplant.users.api;

import com.appus.homeplant.users.api.dto.EmailAndPhoneDto;
import com.appus.homeplant.users.service.PasswordSearchService;
import com.appus.homeplant.users.service.dto.ChangePasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/password")
public class PasswordSearchController {

    private final PasswordSearchService passwordSearchService;

    @ResponseBody
    @PostMapping("/search/auth-code/sending")
    public ResponseEntity<Void> sendAuthCode(
            @RequestBody @Valid EmailAndPhoneDto emailAndPhoneDto) {
        passwordSearchService.sendAuthenticationNumber(
                emailAndPhoneDto.getEmail(), emailAndPhoneDto.getPhone()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/search/auth-code/{phone}/{code}")
    public ResponseEntity<Void> authenticateAuthCode(
            @PathVariable @Pattern(regexp = "010[0-9]{7,8}") String phone,
            @PathVariable @Pattern(regexp = "[0-9]{6}") String code
    ) {
        passwordSearchService.authenticateCode(phone, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/change")
    public ResponseEntity<Void> signUp(@RequestBody @Valid ChangePasswordDto changePasswordDto) {

        passwordSearchService.changePassword(changePasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
