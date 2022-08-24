package com.example.demo.service;

import antlr.Token;
import com.example.demo.controller.dto.AccountRequestDto;
import com.example.demo.controller.dto.AccountResponseDto;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.controller.dto.TokenRequestDto;
import com.example.demo.domain.*;
import com.example.demo.provider.TokenProvider;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AccountRoleRepository;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final TokenProvider tokenProvider;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AccountResponseDto signup(AccountRequestDto accountRequestDto) {
        if(accountRepository.existsByEmail(accountRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Account account = accountRequestDto.toAccount(passwordEncoder);
        Account account1 = accountRepository.save(account);
        Role role = new Role();
        role.setRoleName(UserRole.ROLE_ADMIN);
        Role role1 = roleRepository.save(role);
        AccountRole accountRole = new AccountRole(account1, role1);
        accountRoleRepository.save(accountRole);
        return AccountResponseDto.of(accountRepository.save(account));
    }
    @Transactional
    public TokenDto login(AccountRequestDto accountRequestDto) {
        // 1. Generate AuthenticationToken with LoginID/PW
        UsernamePasswordAuthenticationToken authenticationToken = accountRequestDto.toAuthentication(); //Email/password
        // 2. Real verify (Account Password check)
        // when authenticate run -> run loadByUsername in CustomUserDetailsService
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. Generate JWT Token with authentication info
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        // 4. save RefreshToken
        RefreshToken refreshToken = RefreshToken.builder()
                .key(Long.parseLong(authentication.getName()))
                .value(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        // 5. issue Token
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. verify Refresh Token
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token is not Valid");
        }
        // 2. get Account id form AccessToken
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        // 3. get RefreshToken from storage with Acocunt id
        RefreshToken refreshToken = refreshTokenRepository.findByAccountId(Long.parseLong(authentication.getName()))
                .orElseThrow(()-> new RuntimeException("Logout User"));
        // 4. verifying Refresh Token is equal
        if(!refreshToken.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Not equals UserInfo of Token");
        }
        // 5. Generate New Token
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. update Storage's information
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // issue token
        return tokenDto;
    }
}
