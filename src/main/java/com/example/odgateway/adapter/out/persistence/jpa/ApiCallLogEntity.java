package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.domain.model.ApiCallLog;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TBL_API_CALL_LOG")
class ApiCallLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "URI")
    private String uri;

    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Column(name = "REG_DATE_TIME")
    private LocalDateTime regDateTime;

    @Builder
    ApiCallLogEntity(Long id, String username, String uri, String httpMethod,
        LocalDateTime regDateTime) {
        this.id = id;
        this.username = username;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.regDateTime = regDateTime;
    }

    static ApiCallLogEntity of(ApiCallLog domain) {
        return ApiCallLogEntity.builder()
            .username(domain.username())
            .uri(domain.uri())
            .httpMethod(domain.httpMethod())
            .regDateTime(domain.regDateTime())
            .build();
    }
}
