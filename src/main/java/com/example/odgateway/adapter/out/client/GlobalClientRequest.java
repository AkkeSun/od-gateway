package com.example.odgateway.adapter.out.client;

import static com.example.odgateway.infrastructure.util.QueryStringUtil.getRequestParamMap;

import com.example.odgateway.domain.model.GatewayRule;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.util.MultiValueMap;

@Builder
public record GlobalClientRequest(
    String baseUrl,
    String uri,
    String httpMethod,
    MultiValueMap<String, String> queryParams,
    String requestBody
) {

    public static GlobalClientRequest of(
        GatewayRule gatewayRule, HttpServletRequest request, String body
    ) {
        return GlobalClientRequest.builder()
            .baseUrl(gatewayRule.serviceUrl())
            .httpMethod(gatewayRule.httpMethod())
            .uri(request.getRequestURI())
            .requestBody(body)
            .queryParams(getRequestParamMap(request))
            .build();
    }
}