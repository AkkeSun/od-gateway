package com.example.odgateway.adapter.out.persistence.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface GatewayRuleRepository extends JpaRepository<GatewayRuleEntity, Long> {

    @Query("select distinct rule "
        + "from GatewayRuleEntity rule "
        + "join fetch rule.role "
        + "join fetch rule.serviceInfo "
        + "where rule.status = true "
        + "order by rule.sortOrder")
    List<GatewayRuleEntity> findAll();
}
