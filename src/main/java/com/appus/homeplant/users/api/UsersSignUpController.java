package com.appus.homeplant.users.api;

import com.appus.homeplant.users.service.UserSignUpService;
import com.appus.homeplant.users.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersSignUpController {

    private final UserSignUpService userSignUpService;

    @ResponseBody
    @PostMapping("/auth-code/sending/{phone}")
    public ResponseEntity<Void> sendAuthCode(
            @PathVariable @Pattern(regexp = "010[0-9]{7,8}") String phone
    ) {
        userSignUpService.sendAuthenticationNumber(phone);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/auth-code/{phone}/{code}")
    public ResponseEntity<Void> authenticateAuthCode(
            @PathVariable @Pattern(regexp = "010[0-9]{7,8}") String phone,
            @PathVariable @Pattern(regexp = "[0-9]{6}") String code
    ) {
        userSignUpService.authenticateCode(phone, code);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserDto userDTO) {

        userSignUpService.signUpUsers(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
