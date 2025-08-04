package com.example.odgateway.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record GatewayRule(
    int sortOrder,
    String httpMethod,
    String uriPattern,
    String serviceUrl,
    String role,
    LocalDateTime regDateTime
) {

    public boolean isAnonymous() {
        return role.equals("ANONYMOUS");
    }
}