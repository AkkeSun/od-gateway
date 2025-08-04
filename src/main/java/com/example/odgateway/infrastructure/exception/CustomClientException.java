package com.example.odgateway.infrastructure.exception;

import com.example.odgateway.adapter.out.client.GlobalClientResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomClientException extends RuntimeException {

    private final HttpStatus status;
    private final JsonNode body;

    public CustomClientException(GlobalClientResponse clientResponse) {
        this.status = clientResponse.status();
        this.body = clientResponse.body();
    }
}
