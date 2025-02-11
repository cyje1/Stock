package com.example.stock.web;

import com.example.stock.model.Auth;
import com.example.stock.security.TokenProvider;
import com.example.stock.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/auth")
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 로그인 아이디, 패스워드 검증
        var member = this.memberService.authenticate(request);

        // 토큰 생성 후 반환
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());
        log.info("user log in -> " + request.getUsername());
        return ResponseEntity.ok(token);
    }

}
