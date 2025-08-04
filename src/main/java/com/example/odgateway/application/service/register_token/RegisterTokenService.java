package com.example.odgateway.application.service.register_token;

import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.application.port.in.RegisterTokenUseCase;
import com.example.odgateway.application.port.in.command.RegisterTokenCommand;
import com.example.odgateway.application.port.out.AccountStoragePort;
import com.example.odgateway.application.port.out.RedisStoragePort;
import com.example.odgateway.domain.model.Account;
import com.example.odgateway.domain.model.RefreshTokenInfo;
import com.example.odgateway.domain.model.Role;
import com.example.odgateway.infrastructure.util.JwtUtil;
import com.example.odgateway.infrastructure.util.UserAgentUtil;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class RegisterTokenService implements RegisterTokenUseCase {

    @Value("${spring.data.redis.key.token}")
    private String tokenRedisKey;
    @Value("${spring.data.redis.ttl.refresh-token}")
    private Long refreshTokenTtl;
    private final JwtUtil jwtUtil;
    private final UserAgentUtil userAgentUtil;
    private final RedisStoragePort redisStoragePort;
    private final AccountStoragePort accountStoragePort;

    @Override
    public RegisterTokenServiceResponse registerToken(RegisterTokenCommand command) {
        Account account = accountStoragePort.findByUsernameAndPassword(command);
        RefreshTokenInfo refreshTokenInfo = RefreshTokenInfo.builder()
            .username(account.getUsername())
            .refreshToken(jwtUtil.createRefreshToken(command.username()))
            .userAgent(userAgentUtil.getUserAgent())
            .roles(account.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.joining(",")))
            .build();

        redisStoragePort.register(
            String.format(tokenRedisKey, account.getUsername()),
            toJsonString(refreshTokenInfo),
            refreshTokenTtl
        );

        return RegisterTokenServiceResponse.builder()
            .accessToken(jwtUtil.createAccessToken(account))
            .refreshToken(refreshTokenInfo.getRefreshToken())
            .build();
    }
}
