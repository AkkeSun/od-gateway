package com.example.odgateway.domain.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenInfo {

    private String username;
    private String userAgent;
    private String refreshToken;
    private List<String> roles;

    @Builder
    public RefreshTokenInfo(String username, String userAgent, String refreshToken,
        List<String> roles) {
        this.username = username;
        this.userAgent = userAgent;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    public boolean isValid(String userAgent, String refreshToken) {
        return this.refreshToken.equals(refreshToken) && this.userAgent.equals(userAgent);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Account toAccount() {
        return Account.builder()
            .username(username)
            .roles(roles.stream()
                .map(role -> Role.builder()
                    .name(role)
                    .build())
                .toList())
            .build();
    }
}
