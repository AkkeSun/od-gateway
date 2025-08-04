package com.example.odgateway.domain.model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ApiCallLog(
    String username,
    String uri,
    String httpMethod,
    LocalDateTime regDateTime
) {

    public static ApiCallLog of(HttpServletRequest request, ObjectNode account) {
        return ApiCallLog.builder()
            .username(account.has("username") ?
                account.get("username").asText() : "")
            .uri(request.getRequestURI())
            .httpMethod(request.getMethod())
            .regDateTime(LocalDateTime.now())
            .build();
    }
}
