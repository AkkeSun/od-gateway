package com.example.odgateway.adapter.in.find_account;

import com.example.odgateway.application.port.in.FindAccountUseCase;
import com.example.odgateway.application.service.find_account.FindAccountServiceResponse;
import com.example.odgateway.domain.model.Account;
import com.example.odgateway.infrastructure.resolver.LoginAccount;
import com.example.odgateway.infrastructure.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class FindAccountController {

    private final FindAccountUseCase useCase;

    @GetMapping("/accounts")
    ApiResponse<FindAccountResponse> findAccountInfo(@LoginAccount Account account) {
        FindAccountServiceResponse serviceResponse = useCase.findAccount(account);

        return ApiResponse.ok(FindAccountResponse.of(serviceResponse));
    }
}
