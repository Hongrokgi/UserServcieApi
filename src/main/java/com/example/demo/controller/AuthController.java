package com.example.demo.controller;

import com.example.demo.controller.dto.AccountRequestDto;
import com.example.demo.controller.dto.AccountResponseDto;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.controller.dto.TokenRequestDto;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    //Register
    @PostMapping("/signup")
    public ResponseEntity<AccountResponseDto> signup(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(authService.signup(accountRequestDto));
    }
    //Login
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AccountRequestDto accountRequestDto) {
        ResponseEntity<TokenDto> response = ResponseEntity.ok(authService.login(accountRequestDto));
        return ResponseEntity.ok(authService.login(accountRequestDto));
    }
    //reIssue
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
