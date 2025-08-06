package com.example.odgateway.application.service.update_token;

import static com.example.odgateway.infrastructure.exception.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.application.port.in.UpdateTokenUseCase;
import com.example.odgateway.application.port.out.RedisStoragePort;
import com.example.odgateway.domain.model.RefreshTokenInfo;
import com.example.odgateway.infrastructure.exception.CustomAuthenticationException;
import com.example.odgateway.infrastructure.util.JwtUtil;
import com.example.odgateway.infrastructure.util.UserAgentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
class UpdateTokenService implements UpdateTokenUseCase {

    @Value("${spring.data.redis.key.token}")
    private String refreshTokenKey;

    @Value("${spring.data.redis.ttl.refresh-token}")
    private long refreshTokenTtl;

    private final JwtUtil jwtUtil;

    private final UserAgentUtil userAgentUtil;

    private final RedisStoragePort redisStoragePort;

    @Override
    public UpdateTokenServiceResponse update(String refreshToken) {
        if (!jwtUtil.validateTokenExceptExpiration(refreshToken)) {
            throw new CustomAuthenticationException(INVALID_REFRESH_TOKEN);
        }

        String username = jwtUtil.getClaims(refreshToken).getSubject();
        String redisKey = String.format(refreshTokenKey, username);

        RefreshTokenInfo tokenInfo = redisStoragePort.findData(redisKey, RefreshTokenInfo.class);
        if (ObjectUtils.isEmpty(tokenInfo) ||
            !tokenInfo.isValid(userAgentUtil.getUserAgent(), refreshToken)) {
            throw new CustomAuthenticationException(INVALID_REFRESH_TOKEN);
        }

        tokenInfo.updateRefreshToken(jwtUtil.createRefreshToken(username));
        redisStoragePort.register(redisKey, toJsonString(tokenInfo), refreshTokenTtl);
        return UpdateTokenServiceResponse.builder()
            .accessToken(jwtUtil.createAccessToken(tokenInfo.toAccount()))
            .refreshToken(tokenInfo.getRefreshToken())
            .build();
    }
}
