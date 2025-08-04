package com.example.odgateway.adapter.in.register_token;

import com.example.odgateway.application.service.register_token.RegisterTokenServiceResponse;
import lombok.Builder;

@Builder
record RegisterTokenResponse(
    String accessToken,
    String refreshToken
) {

    static RegisterTokenResponse of(RegisterTokenServiceResponse serviceResponse) {
        return new RegisterTokenResponse(
            serviceResponse.accessToken(),
            serviceResponse.refreshToken()
        );
    }
}

