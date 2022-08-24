package com.example.demo.controller;

import com.example.demo.controller.dto.AccountResponseDto;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountResponseDto> getMyAccountInfo() {
        return ResponseEntity.ok(accountService.getMyInfo());
    }

    @GetMapping("/{email}")
    public ResponseEntity<AccountResponseDto> getAccountInfo(@PathVariable String email) {
        return ResponseEntity.ok(accountService.getAccountInfo(email));
    }
}
