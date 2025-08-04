package com.example.odgateway.infrastructure.filter;

import com.example.odgateway.application.port.out.ApiCallLogStoragePort;
import com.example.odgateway.domain.model.ApiCallLog;
import com.example.odgateway.infrastructure.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/*
    Filter / interceptor Level Error Log
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApiCallLogFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ApiCallLogStoragePort apiCallLogStoragePort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // response body
        String responseBody = new String(wrappedResponse.getContentAsByteArray(),
            StandardCharsets.UTF_8);

        // xx99 인 경우로 변경
        if ((responseBody.contains("BAD_REQUEST") && responseBody.contains("errorCode\":1001")) ||
            (responseBody.contains("UNAUTHORIZED") && responseBody.contains("errorCode\":3001")) ||
            (responseBody.contains("FORBIDDEN") && responseBody.contains("errorCode\":5001"))) {

            String method = request.getMethod();
            String uri = request.getRequestURI();

            ObjectNode requestInfo = new ObjectMapper().createObjectNode();
            ObjectNode requestParam = new ObjectMapper().createObjectNode();
            ObjectNode requestBody = new ObjectMapper().createObjectNode();
            ObjectNode account = new ObjectMapper().createObjectNode();

            // request parameter
            request.getParameterMap().forEach((key, value) -> {
                requestParam.put(key, String.join(",", value));
            });

            // request body
            try {
                String requestBodyStr = new String(wrappedRequest.getContentAsByteArray(),
                    StandardCharsets.UTF_8);
                requestBody = (ObjectNode) new ObjectMapper().readTree(requestBodyStr);
            } catch (Exception ignored) {
            }

            // account info
            String token = request.getHeader("Authorization");
            if (token != null) {
                account = jwtUtil.getAccountInfo(token);
            }

            requestInfo.put("param", requestParam);
            requestInfo.put("body", requestBody);
            requestInfo.put("account", account);

            if (!request.getRequestURI().contains("/docs") && !uri.equals("/favicon.ico")) {
                apiCallLogStoragePort.register(ApiCallLog.of(request, account));
                log.info("[{} {}] request - {}", method, uri, requestInfo);
                log.info("[{} {}] response - {}", method, uri, responseBody);
            }
        }
        wrappedResponse.copyBodyToResponse();
    }
}
