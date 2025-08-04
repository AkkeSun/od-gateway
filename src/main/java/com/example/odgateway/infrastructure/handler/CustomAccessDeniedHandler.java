package com.example.odgateway.infrastructure.handler;

import static com.example.odgateway.infrastructure.util.JsonUtil.toJsonString;

import com.example.odgateway.infrastructure.exception.ErrorCode;
import com.example.odgateway.infrastructure.exception.ErrorResponse;
import com.example.odgateway.infrastructure.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String responseBody = toJsonString(ApiResponse.of(
            HttpStatus.FORBIDDEN,
            ErrorResponse.builder()
                .errorCode(ErrorCode.ACCESS_DENIED_BY_SECURITY.getCode())
                .errorMessage(ErrorCode.ACCESS_DENIED_BY_SECURITY.getMessage())
                .build()));
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.FORBIDDEN.value());
        res.getWriter().write(responseBody);
    }
}
