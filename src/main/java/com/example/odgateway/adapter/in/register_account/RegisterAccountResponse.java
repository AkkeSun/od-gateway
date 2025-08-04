package com.example.odgateway.adapter.in.register_account;
import com.example.odgateway.application.service.register_account.RegisterAccountServiceResponse;
import lombok.Builder;

@Builder
record RegisterAccountResponse(
    Boolean result,
    String username
) {

    static RegisterAccountResponse of(RegisterAccountServiceResponse serviceResponse){
        return RegisterAccountResponse.builder()
            .result(serviceResponse.result())
            .username(serviceResponse.username())
            .build();
    }
}
