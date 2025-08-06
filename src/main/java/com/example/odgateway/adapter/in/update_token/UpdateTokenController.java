package com.example.odgateway.adapter.in.update_token;

import com.example.odgateway.application.port.in.UpdateTokenUseCase;
import com.example.odgateway.application.service.update_token.UpdateTokenServiceResponse;
import com.example.odgateway.infrastructure.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateTokenController {

    private final UpdateTokenUseCase useCase;

    @PutMapping("/auth")
    ApiResponse<UpdateTokenResponse> update(
        @RequestBody @Valid UpdateTokenRequest request) {
        UpdateTokenServiceResponse serviceResponse = useCase.update(request.refreshToken());

        return ApiResponse.ok(UpdateTokenResponse.of(serviceResponse));
    }
}
