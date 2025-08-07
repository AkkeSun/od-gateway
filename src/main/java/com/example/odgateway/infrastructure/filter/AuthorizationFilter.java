package com.example.odgateway.infrastructure.filter;

import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.application.port.out.GatewayRuleStoragePort;
import com.example.odgateway.application.port.out.RedisStoragePort;
import com.example.odgateway.domain.model.GatewayRule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.PathContainer;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

/*
    [ Rule ]
    1. 등록된 인가 정책의 권한이 ANONYMOUS 인 경우 누구나 접근 허용
    2. 인증받은 사용자의 권한이 ROLE_ADMIN 인 경우 모든 요청 허용
    3. 등록된 인가 정책에 따라 접근 허용
    4. 미등록된 URI 의 경우 인증받은 사용자라면 누구나 접근 허용
 */
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${spring.data.redis.key.rule}")
    private String gatewayRuleKey;
    @Value("${spring.data.redis.ttl.rule}")
    private long gatewayRuleTtl;
    private final PathPatternParser parser;
    private final RedisStoragePort redisStoragePort;
    private final GatewayRuleStoragePort gatewayRuleStoragePort;

    AuthorizationFilter(RedisStoragePort redisStoragePort,
        GatewayRuleStoragePort gatewayRuleStoragePort
    ) {
        this.parser = new PathPatternParser();
        this.redisStoragePort = redisStoragePort;
        this.gatewayRuleStoragePort = gatewayRuleStoragePort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain filterChain) throws ServletException, IOException {

        String requestUri = req.getRequestURI();
        String requestMethod = req.getMethod();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (GatewayRule rule : getAuthorizationRules()) {
            if (!rule.httpMethod().equalsIgnoreCase(requestMethod)) {
                continue;
            }

            PathPattern pattern = parser.parse(rule.uriPattern());
            if (!pattern.matches(PathContainer.parsePath(requestUri))) {
                continue;
            }

            if (rule.isAnonymous()) {
                filterChain.doFilter(req, res);
                return;
            }

            List<String> userRoles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

            if (userRoles.contains("ROLE_ADMIN")) {
                filterChain.doFilter(req, res);
                return;
            }

            if (userRoles.contains(rule.role())) {
                filterChain.doFilter(req, res);
                return;

            } else {
                throw new AccessDeniedException("e");
            }
        }

        if (auth.getPrincipal().toString().contains("anonymous")) {
            throw new AccessDeniedException("e");
        }
        filterChain.doFilter(req, res);
    }

    private List<GatewayRule> getAuthorizationRules() {
        List<GatewayRule> rules = redisStoragePort.findDataList(gatewayRuleKey, GatewayRule.class);
        if (rules.isEmpty()) {
            rules = gatewayRuleStoragePort.findAll();
            redisStoragePort.register(gatewayRuleKey, toJsonString(rules), gatewayRuleTtl);
        }
        return rules;
    }
}
