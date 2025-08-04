package com.example.odgateway.infrastructure.util;

import com.example.odgateway.domain.model.Account;
import com.example.odgateway.infrastructure.exception.CustomAuthenticationException;
import com.example.odgateway.infrastructure.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtilImpl implements JwtUtil {
    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${spring.data.redis.ttl.access-token}")
    private long tokenValidTime;

    @Value("${spring.data.redis.ttl.refresh-token}")
    private long refreshTokenValidTime;

    @Override
    public String createAccessToken(Account account) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(account.getUsername());
        claims.put("roles", account.getRoleNames());
        return "Bearer " + Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    @Override
    public String createRefreshToken(String username) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(username);
        return "Bearer " + Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    @Override
    public boolean validateTokenExceptExpiration(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        try {
            token = token.replace("Bearer ", "");
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new CustomAuthenticationException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    @Override
    public ObjectNode getAccountInfo(String token) {
        ObjectNode userInfo = new ObjectMapper().createObjectNode();
        try {
            Claims claims = getClaims(token);
            userInfo.put("username", claims.getSubject());
            userInfo.putPOJO("roles", claims.get("roles"));
            return userInfo;
        } catch (Exception e) {
            return new ObjectMapper().createObjectNode();
        }
    }
}
