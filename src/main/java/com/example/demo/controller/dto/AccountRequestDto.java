package com.example.demo.controller.dto;

import com.example.demo.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDto {
    private String email;
    private String userName;
    private String password;

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .email(email)
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
