package com.example.odgateway.application.service.find_account;

import com.example.odgateway.application.port.in.FindAccountUseCase;
import com.example.odgateway.application.port.out.AccountStoragePort;
import com.example.odgateway.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FindAccountService implements FindAccountUseCase {

    private final AccountStoragePort accountStoragePort;

    @Override
    public FindAccountServiceResponse findAccount(Account account) {
        Account savedAccount = accountStoragePort.findByUsername(account.getUsername());
        return FindAccountServiceResponse.of(savedAccount);
    }
}

