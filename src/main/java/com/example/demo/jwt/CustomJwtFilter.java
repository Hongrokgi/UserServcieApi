package com.example.demo.jwt;

import com.example.demo.controller.dto.ErrorHandlerDto;
import com.example.demo.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RequiredArgsConstructor
@Configuration
public class CustomJwtFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    public ErrorHandlerDto doFilter(HttpServletRequest request) throws IOException, ServletException, NullPointerException {
        ErrorHandlerDto dto = new ErrorHandlerDto();
        String jwt = resolveToken(request);
        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals("ROLE_ADMIN")) {
                    dto.setStatusCode(200);
                    dto.setRole(authority.getAuthority());
                    return dto;
                }else {
                    dto.setStatusCode(403);
                    dto.setRole(authority.getAuthority());
                    return dto;
                }
            }
        }
        dto.setStatusCode(404);
        dto.setRole("ROLE_ANONYMOUS");
        return dto;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
