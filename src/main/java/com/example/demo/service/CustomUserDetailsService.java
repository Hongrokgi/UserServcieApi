package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.domain.AccountRole;
import com.example.demo.domain.Role;
import com.example.demo.domain.UserRole;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.AccountRoleRepository;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;

    private final RoleRepository roleRepository;
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username).map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException(username + "-> Not Found in the database"));
    }

    private UserDetails createUserDetails(Account account) {
        Optional<AccountRole> accountRole = accountRoleRepository.findById(account.getId());
        Optional<Role> role = roleRepository.findById(accountRole.get().getId());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.get().getRoleName().toString());
        return new User(String.valueOf(account.getId()), account.getPassword(), Collections.singleton(grantedAuthority));
    }
}
