package com.ll.rest.boundedContext.member.controller;

import com.ll.rest.base.rsData.RsData;
import com.ll.rest.boundedContext.member.entity.Member;
import com.ll.rest.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/member", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class ApiV1MemberController {
    private final MemberService memberService;

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    //@AllArgsConstructor
    @Getter
    public static class LoginResponse {
        private final String accessToken;

        public LoginResponse(String accessToken) {
            this.accessToken = accessToken;
        }
    }

    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String accessToken = memberService.genAccessToken(loginRequest.getUsername(), loginRequest.getPassword());

        //resp.addHeader("Authentication", accessToken);

        return RsData.of("S-1", "엑세스 토큰이 생성되었습니다.", new LoginResponse(accessToken));
    }

    @AllArgsConstructor
    @Getter
    public static class MeResponse {
        private final Member member;
    }

    // consumes = ALL_VALUE => 나는 딱히 JSON을 입력받기를 고집하지 않겠다.
    // 매개변수로 뭐가 오던 상관없다. null도 된다.
    @GetMapping(value = "/me", consumes = ALL_VALUE)
    public RsData<MeResponse> me(@AuthenticationPrincipal User user){
        Member member = memberService.findByUsername(user.getUsername()).get();

        return RsData.of("S-1", "엑세스 토큰이 생성되었습니다.", new MeResponse(member));
    }
}
