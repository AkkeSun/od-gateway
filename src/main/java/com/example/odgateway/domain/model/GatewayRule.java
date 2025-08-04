package com.example.odgateway.domain.model;

import lombok.Builder;

@Builder
public record GatewayRule(
    int sortOrder,
    String httpMethod,
    String uriPattern,
    String serviceUrl,
    String role
) {

    public boolean isAnonymous() {
        return role.equals("ANONYMOUS");
    }
}