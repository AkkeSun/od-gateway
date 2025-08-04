package com.example.odgateway.adapter.in.gateway;

import com.example.odgateway.application.port.in.GatewayUseCase;
import com.example.odgateway.application.service.gateway.GatewayServiceResponse;
import com.example.odgateway.infrastructure.response.ApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class GatewayController {

    private final GatewayUseCase useCase;

    @RequestMapping("/**")
    ApiResponse<JsonNode> gateway(HttpServletRequest request,
        @RequestBody(required = false) String body
    ) {
        GatewayServiceResponse serviceResponse = useCase.getResponse(request, body);
        return ApiResponse.ok(serviceResponse.response());
    }
}