package com.example.odgateway.application.service.update_token;

import lombok.Builder;

@Builder
public record UpdateTokenServiceResponse(
    String accessToken,
    String refreshToken
) {

}
