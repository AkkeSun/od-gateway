package com.example.odgateway.application.port.in;


import com.example.odgateway.application.service.update_token.UpdateTokenServiceResponse;

public interface UpdateTokenUseCase {

    UpdateTokenServiceResponse update(String refreshToken);
}
