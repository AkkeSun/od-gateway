package com.example.odgateway.adapter.in.update_token;

import lombok.Builder;

@Builder
record UpdateTokenRequest(
    String refreshToken
) {

}
