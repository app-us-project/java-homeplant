package com.appus.homeplant.users.api;

import com.appus.homeplant.users.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersSignUpController {

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserDTO userDTO) {

        System.out.println(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
