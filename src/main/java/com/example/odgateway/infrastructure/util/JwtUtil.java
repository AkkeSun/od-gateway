package com.example.odgateway.infrastructure.util;

import com.example.odgateway.domain.model.Account;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;

public interface JwtUtil {

    String createAccessToken(Account account);

    String createRefreshToken(String username);

    boolean validateTokenExceptExpiration(String token);

    Claims getClaims(String token);

    ObjectNode getAccountInfo(String token);
}
