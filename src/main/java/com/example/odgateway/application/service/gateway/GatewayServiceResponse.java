package com.example.odgateway.application.service.gateway;

import com.example.odgateway.adapter.out.client.GlobalClientResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;

@Builder
public record GatewayServiceResponse(
    JsonNode response
) {

    public static GatewayServiceResponse of(GlobalClientResponse clientResponse) {
        return GatewayServiceResponse.builder()
            .response(clientResponse.body())
            .build();
    }
}
