package com.example.odgateway.application.port.in;

import com.example.odgateway.application.service.find_account.FindAccountServiceResponse;
import com.example.odgateway.domain.model.Account;

public interface FindAccountUseCase {

    FindAccountServiceResponse findAccount(Account account);
}
