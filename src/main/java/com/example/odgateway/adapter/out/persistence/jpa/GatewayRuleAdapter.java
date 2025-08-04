package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.application.port.out.GatewayRuleStoragePort;
import com.example.odgateway.domain.model.GatewayRule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class GatewayRuleAdapter implements GatewayRuleStoragePort {

    private final GatewayRuleRepository repository;

    @Override
    public List<GatewayRule> findAll() {
        return repository.findAll().stream()
            .map(GatewayRuleEntity::toDomain)
            .toList();
    }
}
