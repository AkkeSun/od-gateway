package com.example.odgateway.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshTokenInfo {

    private String username;
    private String userAgent;
    private String refreshToken;
    private String roles;

    @Builder
    public RefreshTokenInfo(Long accountId, String username, String userAgent, String refreshToken,
        String roles) {
        this.username = username;
        this.userAgent = userAgent;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }
}
