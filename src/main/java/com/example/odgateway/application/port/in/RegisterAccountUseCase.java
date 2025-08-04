package com.example.odgateway.application.port.in;
import com.example.odgateway.application.port.in.command.RegisterAccountCommand;
import com.example.odgateway.application.service.register_account.RegisterAccountServiceResponse;

public interface RegisterAccountUseCase {

    RegisterAccountServiceResponse register(RegisterAccountCommand command);
}
