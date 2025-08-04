package com.example.odgateway.application.service.register_account;

import static com.example.odgateway.infrastructure.exception.ErrorCode.Business_SAVED_ACCOUNT_INFO;
import static com.example.odgateway.infrastructure.exception.ErrorCode.INVALID_ROLE;
import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.application.port.in.RegisterAccountUseCase;
import com.example.odgateway.application.port.in.command.RegisterAccountCommand;
import com.example.odgateway.application.port.out.AccountStoragePort;
import com.example.odgateway.application.port.out.RedisStoragePort;
import com.example.odgateway.application.port.out.RoleStoragePort;
import com.example.odgateway.domain.model.Account;
import com.example.odgateway.domain.model.Role;
import com.example.odgateway.infrastructure.exception.CustomBusinessException;
import com.example.odgateway.infrastructure.exception.CustomValidationException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RegisterAccountService implements RegisterAccountUseCase {

    @Value("${spring.data.redis.key.role}")
    private String roleRedisKey;
    @Value("${spring.data.redis.ttl.role}")
    private long roleRedisTTL;
    private final RoleStoragePort roleStoragePort;
    private final RedisStoragePort redisStoragePort;
    private final AccountStoragePort accountStoragePort;

    @Override
    public RegisterAccountServiceResponse register(RegisterAccountCommand command) {
        Map<String, Role> validRoles = getValidRoles();
        if (!isValidRole(command, validRoles)) {
            throw new CustomValidationException(INVALID_ROLE);
        }
        if (accountStoragePort.existsByUsername(command.username())) {
            throw new CustomBusinessException(Business_SAVED_ACCOUNT_INFO);
        }

        Account savedAccount = accountStoragePort.register(Account.of(command, validRoles));
        return RegisterAccountServiceResponse.of(savedAccount);
    }

    private boolean isValidRole(RegisterAccountCommand command, Map<String, Role> validRoles) {
        for (String role : command.roles()) {
            if (!validRoles.containsKey(role)) {
                return false;
            }
        }
        return true;
    }

    private Map<String, Role> getValidRoles() {
        List<Role> roles = redisStoragePort.findDataList(roleRedisKey, Role.class);
        if (roles.isEmpty()) {
            roles = roleStoragePort.findAll();
            redisStoragePort.register(roleRedisKey, toJsonString(roles), roleRedisTTL);
        }
        return roles.stream().collect(Collectors.toMap(Role::name, Function.identity()));
    }
}
