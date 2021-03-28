package com.appus.homeplant.users.api;

import com.appus.homeplant.users.api.dto.EmailAndPhoneDto;
import com.appus.homeplant.users.service.MypageService;
import com.appus.homeplant.users.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class MyPageController {
    private final MypageService mypageService;

    /**
     * TODO
     * 받아오고자 하는 유저 정보를 URI 에 나타내주면 REST api 가 조금 더 명시적이게 될 것 같아요~
     * 아래와 같이 @GetMapping 을 사용하면 클라이언트에 Get 방식의 API 를 제공할 수 있어요.
     * 그리고 그 Path 안에 다음과 같이 {} 로 감싸게 되면 URI 데이터를 바로 받아 올 수 있어요.
     * 이는 @PathVariable 과 관련이 되어 있어요~
     *
     * ex) GET /users/1234 -> 1234가 @PathVariable 에 의해 userId로 지정이 되어서 메서드의
     * 파라미터로 전달됩니다~
     */
    @ResponseBody
    @GetMapping("/{userId}")
    public EmailAndPhoneDto updateMyPage(@PathVariable Long userId, UserDto userDto) {
        /**
         * TODO
         * 인증 정보는 SecurityContextHolder 의 Context 에 Authentication 이라는 인터페이스로
         * 추상화되어 저장되어 있습니다. 해당 인증 정보는 우리가 JwtTokenFilter 라는 클래스에서 처리를
         * 해주었죠 :)
         * JwtTokenFilter 의 46라인을 확인해보세요~
         */
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        Long authorizedUserId = Long.parseLong((String) authentication.getPrincipal());
        if (!authorizedUserId.equals(userId)) {
            throw new IllegalArgumentException("접근할 수 없는 유저 정보입니다.");
        }

        return mypageService.getUser(userId);
    }
}
