package com.appus.homeplant.users.api;

import com.appus.homeplant.users.service.MypageService;
import com.appus.homeplant.users.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/mypage")
public class MyPageController {
    private final MypageService mypageService;

    public String updateMypage(UserDto userDto) {
        String jwt=userDto.
    }
}
