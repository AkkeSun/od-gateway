package com.example.odgateway.application.port.in;

import com.example.odgateway.application.service.gateway.GatewayServiceResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface GatewayUseCase {

    GatewayServiceResponse getResponse(HttpServletRequest request, String body);
}
