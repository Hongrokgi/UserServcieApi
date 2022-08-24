package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long accountId;
    private String refreshToken;

    @Builder
    public RefreshToken(Long key, String value) {
        this.accountId=key;
        this.refreshToken=value;
    }



    public RefreshToken updateValue(String token) {
        this.refreshToken = token;
        return this;
    }
}
