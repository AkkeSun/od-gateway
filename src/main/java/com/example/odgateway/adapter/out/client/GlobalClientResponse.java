package com.example.odgateway.adapter.out.client;

import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonNode;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

@Builder
public record GlobalClientResponse(
    JsonNode body,
    HttpStatus status
) {

    public static GlobalClientResponse ofSuccessResponse(String body) {
        return GlobalClientResponse.builder()
            .body(toJsonNode(body))
            .status(HttpStatus.OK)
            .build();
    }

    public static GlobalClientResponse ofErrorResponse(RestClientResponseException e) {
        return GlobalClientResponse.builder()
            .body(toJsonNode(e.getResponseBodyAsString()))
            .status(HttpStatus.valueOf(e.getStatusCode().value()))
            .build();
    }

    public boolean failed() {
        return status != HttpStatus.OK;
    }
}
