package com.example.odgateway.adapter.out.persistence.jpa;

import com.example.odgateway.application.port.out.ApiCallLogStoragePort;
import com.example.odgateway.domain.model.ApiCallLog;
import org.springframework.stereotype.Component;

@Component
class CallLogAdapter implements ApiCallLogStoragePort {

    @Override
    public void register(ApiCallLog callLog) {

    }
}
