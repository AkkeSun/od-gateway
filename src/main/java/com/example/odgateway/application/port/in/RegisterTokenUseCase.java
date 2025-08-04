package com.example.odgateway.application.port.in;

import com.example.odgateway.application.port.in.command.RegisterTokenCommand;
import com.example.odgateway.application.service.register_token.RegisterTokenServiceResponse;

public interface RegisterTokenUseCase {

    RegisterTokenServiceResponse registerToken(RegisterTokenCommand command);
}
