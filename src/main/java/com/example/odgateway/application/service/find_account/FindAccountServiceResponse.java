package com.example.odgateway.application.service.find_account;

import static com.example.odgateway.infrastructure.util.DateUtil.formatDateTime;

import com.example.odgateway.domain.model.Account;
import com.example.odgateway.domain.model.Role;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record FindAccountServiceResponse(
    Long id,
    String username,
    String name,
    String userTel,
    List<String> roles,
    String regDateTime
) {

    public static FindAccountServiceResponse of(Account account) {
        return FindAccountServiceResponse.builder()
            .id(account.getId())
            .username(account.getUsername())
            .name(account.getName())
            .userTel(account.getUserTel())
            .roles(account.getRoles().stream().map(Role::name)
                .collect(Collectors.toList()))
            .regDateTime(formatDateTime(account.getRegDateTime()))
            .build();
    }
}
