package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.domain.model.GatewayRule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TBL_GATEWAY_RULE")
@NoArgsConstructor
class GatewayRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SORT_ORDER")
    private int sortOrder;

    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Column(name = "URI_PATTERN")
    private String uriPattern;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private RoleEntity role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICE_INFO_ID")
    private ServiceInfoEntity serviceInfo;

    @JoinColumn(name = "STATUS")
    private boolean status;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    GatewayRuleEntity(Long id, int sortOrder, String httpMethod, String uriPattern,
        RoleEntity role, ServiceInfoEntity serviceInfo, boolean status, LocalDateTime regDateTime) {
        this.id = id;
        this.sortOrder = sortOrder;
        this.httpMethod = httpMethod;
        this.uriPattern = uriPattern;
        this.role = role;
        this.serviceInfo = serviceInfo;
        this.status = status;
        this.regDateTime = regDateTime;
    }

    GatewayRule toDomain() {
        return GatewayRule.builder()
            .sortOrder(sortOrder)
            .httpMethod(httpMethod)
            .uriPattern(uriPattern)
            .serviceUrl(serviceInfo.getUrl())
            .role(role.getName())
            .regDateTime(regDateTime)
            .build();
    }
}
