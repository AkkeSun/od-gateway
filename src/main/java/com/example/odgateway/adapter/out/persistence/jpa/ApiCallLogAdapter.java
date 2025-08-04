package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.application.port.out.ApiCallLogStoragePort;
import com.example.odgateway.domain.model.ApiCallLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ApiCallLogAdapter implements ApiCallLogStoragePort {

    private final ApiCallLogRepository repository;

    @Override
    public void register(ApiCallLog apiCallLog) {
        repository.save(ApiCallLogEntity.of(apiCallLog));
    }
}
