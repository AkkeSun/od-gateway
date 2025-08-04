package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.domain.model.Account;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AccountMapper {

    private final PasswordEncoder encoder;

    Account toDomain(AccountEntity entity) {
        return Account.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .password(entity.getPassword())
            .name(entity.getName())
            .userTel(entity.getUserTel())
            .roles(entity.getRoles().stream()
                .map(RoleEntity::toDomain)
                .toList())
            .regDateTime(entity.getRegDateTime())
            .status(entity.isStatus())
            .build();
    }

    AccountEntity toEntity(Account domain) {
        return AccountEntity.builder()
            .id(domain.getId())
            .password(encoder.encode(domain.getPassword()))
            .username(domain.getUsername())
            .name(domain.getName())
            .userTel(domain.getUserTel())
            .roles(domain.getRoles().stream()
                .map(RoleEntity::of)
                .collect(Collectors.toSet()))
            .status(domain.isStatus())
            .regDateTime(domain.getRegDateTime())
            .build();
    }
}