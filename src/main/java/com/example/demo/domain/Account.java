package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "account")
    private List<AccountRole> roles = new ArrayList<>();

    @Builder
    public Account(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
