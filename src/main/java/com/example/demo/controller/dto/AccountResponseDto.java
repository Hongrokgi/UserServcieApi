package com.example.demo.controller.dto;

import com.example.demo.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {
    private String email;
    public static AccountResponseDto of(Account account) {
        return new AccountResponseDto(account.getEmail());
    }
}
