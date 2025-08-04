package com.example.odgateway.application.port.out;

import com.example.odgateway.adapter.out.client.GlobalClientRequest;
import com.example.odgateway.adapter.out.client.GlobalClientResponse;

public interface GlobalClientPort {

    GlobalClientResponse getResponse(GlobalClientRequest request);
}
