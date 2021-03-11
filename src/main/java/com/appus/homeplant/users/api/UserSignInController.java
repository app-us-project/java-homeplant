package com.appus.homeplant.users.api;

import com.appus.homeplant.users.api.dto.JwtResponse;
import com.appus.homeplant.users.api.dto.SignInRequest;
import com.appus.homeplant.users.service.UserSignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserSignInController {
    private final UserSignInService userSignInService;

    //로그인 요청
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid SignInRequest request) {
        String jwt = userSignInService.authenticateAndIssueJWT(
                request.getEmail(), request.getPassword()
        );

        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
    }
}
