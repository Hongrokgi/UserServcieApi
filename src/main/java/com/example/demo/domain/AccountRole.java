package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AccountRole {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Account account;

    @ManyToOne(fetch = LAZY)
    @JoinColumn
    private Role role;

    public AccountRole(Account account, Role role) {
        this.account = account;
        this.role = role;
    }
}
