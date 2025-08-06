package com.example.odgateway.adapter.in.update_token;

import com.example.odgateway.application.service.update_token.UpdateTokenServiceResponse;
import lombok.Builder;

@Builder
record UpdateTokenResponse(
    String accessToken,
    String refreshToken
) {

    static UpdateTokenResponse of(UpdateTokenServiceResponse serviceResponse) {
        return UpdateTokenResponse.builder()
            .accessToken(serviceResponse.accessToken())
            .refreshToken(serviceResponse.refreshToken())
            .build();
    }
}
