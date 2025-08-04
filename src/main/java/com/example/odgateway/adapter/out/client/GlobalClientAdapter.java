package com.example.odgateway.adapter.out.client;

import com.example.odgateway.application.port.out.GlobalClientPort;
import java.time.Duration;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec;
import org.springframework.web.client.RestClientResponseException;

@Component
class GlobalClientAdapter implements GlobalClientPort {

    private final RestClient.Builder restClientBuilder;

    private final ClientHttpRequestFactorySettings settings;

    GlobalClientAdapter(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
        this.settings = ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(Duration.ofSeconds(3))
            .withReadTimeout(Duration.ofSeconds(5));
    }

    @Override
    public GlobalClientResponse getResponse(GlobalClientRequest request) {
        RequestHeadersSpec<?> spec = getResponseHeaderSpec(request);
        return getApiResponse(spec);
    }

    private RequestHeadersSpec<?> getResponseHeaderSpec(GlobalClientRequest request) {
        RestClient client = restClientBuilder.baseUrl(request.baseUrl())
            .requestFactory(ClientHttpRequestFactories.get(settings))
            .build();
        switch (request.httpMethod()) {
            case "GET" -> {
                return client.get()
                    .uri(uriBuilder -> uriBuilder
                        .path(request.uri())
                        .queryParams(request.queryParams())
                        .build());
            }
            case "POST" -> {
                return client.post()
                    .uri(uriBuilder -> uriBuilder
                        .path(request.uri())
                        .queryParams(request.queryParams())
                        .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request.requestBody());
            }
            case "PUT" -> {
                return client.put()
                    .uri(uriBuilder -> uriBuilder
                        .path(request.uri())
                        .queryParams(request.queryParams())
                        .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request.requestBody());
            }
            case "DELETE" -> {
                return client.delete()
                    .uri(uriBuilder -> uriBuilder
                        .path(request.uri())
                        .queryParams(request.queryParams())
                        .build());
            }
            default -> throw new IllegalArgumentException("지원하지 않는 HTTP 메서드");
        }
    }

    private GlobalClientResponse getApiResponse(RequestHeadersSpec<?> spec) {
        try {
            String body = spec.retrieve().body(String.class);
            return GlobalClientResponse.ofSuccessResponse(body);
        } catch (RestClientResponseException e) {
            return GlobalClientResponse.ofErrorResponse(e);
        }
    }
}
