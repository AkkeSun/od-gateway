package com.example.odgateway.application.service.register_account;
import com.example.odgateway.domain.model.Account;
import lombok.Builder;

@Builder
public record RegisterAccountServiceResponse(
    Boolean result,
    String username
) {

    public static RegisterAccountServiceResponse of(Account account) {
        return RegisterAccountServiceResponse.builder()
            .result(true)
            .username(account.getUsername())
            .build();
    }
}
