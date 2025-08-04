package com.example.odgateway.application.port.out;

import com.example.odgateway.domain.model.GatewayRule;
import java.util.List;

public interface GatewayRuleStoragePort {

    List<GatewayRule> findAll();
}
