package com.example.odgateway.adapter.in.register_account;

import com.example.odgateway.application.port.in.RegisterAccountUseCase;
import com.example.odgateway.application.service.register_account.RegisterAccountServiceResponse;
import com.example.odgateway.infrastructure.response.ApiResponse;
import com.example.odgateway.infrastructure.validation.groups.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class RegisterAccountController {

    private final RegisterAccountUseCase useCase;

    @PostMapping("/accounts")
    ApiResponse<RegisterAccountResponse> registerAccount(
        @RequestBody @Validated(ValidationSequence.class) RegisterAccountRequest request) {
        RegisterAccountServiceResponse serviceResponse = useCase.register(request.toCommand());

        return ApiResponse.ok(RegisterAccountResponse.of(serviceResponse));
    }
}