package com.example.odgateway.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // code xx99 == filer level error

    // status code 400 (1001): bad request
    INVALID_ROLE(1001, "유효하지 않은 권한 입니다"),

    // status code 404 (2001 - 2099) : Not found error
    DoesNotExist_CLIENT_INFO(2001, "조회된 클라이언트 정보가 없습니다"),
    DoesNotExist_ACCOUNT_INFO(2002, "조회된 사용자 정보가 없습니다"),

    // status code 401 (3001 - 3099) : Unauthorized
    INVALID_ACCESS_TOKEN(3001, "유효한 인증 토큰이 아닙니다"),
    INVALID_REFRESH_TOKEN(3002, "유효한 리프레시 토큰이 아닙니다"),
    INVALID_ACCESS_TOKEN_BY_SECURITY(3099, "유효한 인증 토큰이 아닙니다"),

    // status code 500 (4001 - 4099) : Internal Server Error
    Business_SAVED_ACCOUNT_INFO(4001, "등록된 사용자 정보 입니다"),
    
    // status code 403 (5001 - 5099) : Forbidden
    ACCESS_DENIED(5001, "접근권한이 없습니다"),
    ACCESS_DENIED_BY_SECURITY(5099, "접근권한이 없습니다"),

    // status code 502 (6001) : Bad Gateway
    CLIENT_CONNECTION_REFUSED(6001, "외부 API 연결에 실패했습니다"),

    ;

    private final int code;
    private final String message;
}