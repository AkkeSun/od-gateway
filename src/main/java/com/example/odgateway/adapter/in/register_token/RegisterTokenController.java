package com.example.odgateway.adapter.in.register_token;

import com.example.odgateway.application.port.in.RegisterTokenUseCase;
import com.example.odgateway.application.service.register_token.RegisterTokenServiceResponse;
import com.example.odgateway.infrastructure.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class RegisterTokenController {

    private final RegisterTokenUseCase registerTokenUseCase;

    @PostMapping("/auth")
    ApiResponse<RegisterTokenResponse> registerToken(
        @RequestBody @Valid RegisterTokenRequest request) {
        RegisterTokenServiceResponse serviceResponse = registerTokenUseCase
            .registerToken(request.toCommand());

        return ApiResponse.ok(RegisterTokenResponse.of(serviceResponse));
    }
}
