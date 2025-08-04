package com.example.odgateway.application.port.out;

import com.example.odgateway.domain.model.ApiCallLog;

public interface ApiCallLogStoragePort {

    void register(ApiCallLog callLog);
}
