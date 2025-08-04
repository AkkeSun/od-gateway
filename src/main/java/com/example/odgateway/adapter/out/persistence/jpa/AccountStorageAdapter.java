package com.example.odgateway.adapter.out.persistence.jpa;

import static com.example.odgateway.infrastructure.exception.ErrorCode.DoesNotExist_ACCOUNT_INFO;

import com.example.odgateway.application.port.in.command.RegisterTokenCommand;
import com.example.odgateway.application.port.out.AccountStoragePort;
import com.example.odgateway.domain.model.Account;
import com.example.odgateway.infrastructure.exception.CustomNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AccountStorageAdapter implements AccountStoragePort {

    private final PasswordEncoder encoder;
    private final AccountMapper mapper;
    private final AccountRepository repository;

    @Override
    public Account findByUsername(String username) {
        AccountEntity entity = repository.findByUsername(username)
            .orElseThrow(() -> new CustomNotFoundException(DoesNotExist_ACCOUNT_INFO));
        return mapper.toDomain(entity);
    }

    @Override
    public Account register(Account account) {
        AccountEntity entity = repository.save(mapper.toEntity(account));
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Account findByUsernameAndPassword(RegisterTokenCommand command) {
        AccountEntity entity = repository.findByUsername(command.username())
            .orElseThrow(() -> new CustomNotFoundException(DoesNotExist_ACCOUNT_INFO));

        if (!encoder.matches(command.password(), entity.getPassword())) {
            throw new CustomNotFoundException(DoesNotExist_ACCOUNT_INFO);
        }
        return mapper.toDomain(entity);
    }
}
