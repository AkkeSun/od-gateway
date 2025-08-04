package com.example.odgateway.application.service.gateway;

import static com.example.odgateway.infrastructure.exception.ErrorCode.DoesNotExist_CLIENT_INFO;
import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.adapter.out.client.GlobalClientRequest;
import com.example.odgateway.adapter.out.client.GlobalClientResponse;
import com.example.odgateway.application.port.in.GatewayUseCase;
import com.example.odgateway.application.port.out.GatewayRuleStoragePort;
import com.example.odgateway.application.port.out.GlobalClientPort;
import com.example.odgateway.application.port.out.RedisStoragePort;
import com.example.odgateway.domain.model.GatewayRule;
import com.example.odgateway.infrastructure.exception.CustomClientException;
import com.example.odgateway.infrastructure.exception.CustomNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

@Service
class GatewayService implements GatewayUseCase {

    @Value("${spring.data.redis.key.rule}")
    private String gatewayRuleKey;
    @Value("${spring.data.redis.ttl.rule}")
    private long gatewayRuleTtl;
    private final PathPatternParser parser;
    private final GlobalClientPort globalClientPort;
    private final RedisStoragePort redisStoragePort;
    private final GatewayRuleStoragePort gatewayRuleStoragePort;

    GatewayService(GlobalClientPort globalClientPort, RedisStoragePort redisStoragePort,
        GatewayRuleStoragePort gatewayRuleStoragePort
    ) {
        this.parser = new PathPatternParser();
        this.globalClientPort = globalClientPort;
        this.redisStoragePort = redisStoragePort;
        this.gatewayRuleStoragePort = gatewayRuleStoragePort;
    }

    @Override
    public GatewayServiceResponse getResponse(HttpServletRequest request, String body) {
        GatewayRule clientInfo = getClientInfo(request);
        GlobalClientRequest clientRequest = GlobalClientRequest.of(clientInfo, request, body);
        GlobalClientResponse clientResponse = globalClientPort.getResponse(clientRequest);
        if (clientResponse.failed()) {
            throw new CustomClientException(clientResponse);
        }

        return GatewayServiceResponse.of(clientResponse);
    }

    private GatewayRule getClientInfo(HttpServletRequest request) {
        for (GatewayRule info : getGatewayRules()) {
            if (!info.httpMethod().equalsIgnoreCase(request.getMethod())) {
                continue;
            }

            PathPattern pattern = parser.parse(info.uriPattern());
            if (pattern.matches(PathContainer.parsePath(request.getRequestURI()))) {
                return info;
            }
        }
        throw new CustomNotFoundException(DoesNotExist_CLIENT_INFO);
    }

    private List<GatewayRule> getGatewayRules() {
        List<GatewayRule> rules = redisStoragePort.findDataList(gatewayRuleKey, GatewayRule.class);
        if (rules.isEmpty()) {
            rules = gatewayRuleStoragePort.findAll();
            redisStoragePort.register(gatewayRuleKey, toJsonString(rules), gatewayRuleTtl);
        }
        return rules;
    }
}
