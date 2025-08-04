package com.example.odgateway.application.port.out;

import com.example.odgateway.application.port.in.command.RegisterTokenCommand;
import com.example.odgateway.domain.model.Account;

public interface AccountStoragePort {

    Account findByUsername(String username);

    Account register(Account account);

    boolean existsByUsername(String username);

    Account findByUsernameAndPassword(RegisterTokenCommand command);
}
