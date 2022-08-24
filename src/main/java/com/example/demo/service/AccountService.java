package com.example.demo.service;

import com.example.demo.controller.dto.AccountResponseDto;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountResponseDto getAccountInfo(String email) {
        return accountRepository.findByEmail(email)
                .map(AccountResponseDto::of)
                .orElseThrow(()-> new RuntimeException("사용자 정보가 없습니다."));
    }
    // 현재 SecurityContext에 있는 사용자 정보 가져오기
    @Transactional(readOnly = true)
    public AccountResponseDto getMyInfo() {
        return accountRepository.findById(SecurityUtil.getCurrentAccountId())
                .map(AccountResponseDto::of)
                .orElseThrow(()-> new RuntimeException("로그인 유저 정보 없습니다."));
    }

}
