package com.example.odgateway.infrastructure.exception;

import static com.example.odgateway.infrastructure.exception.ErrorCode.CLIENT_CONNECTION_REFUSED;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.odgateway.infrastructure.aop.ExceptionHandlerLog;
import com.example.odgateway.infrastructure.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    ApiResponse<Object> bindException(BindException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorResponse.builder()
                .errorCode(1001)
                .errorMessage(e.getBindingResult().getAllErrors().getFirst().getDefaultMessage())
                .build()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiResponse<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorResponse.builder()
                .errorCode(1001)
                .errorMessage(e.getBindingResult().getAllErrors().getFirst().getDefaultMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomValidationException.class)
    ApiResponse<Object> customValidationException(CustomValidationException e) {
        return ApiResponse.of(
            HttpStatus.BAD_REQUEST,
            ErrorResponse.builder()
                .errorCode(1002)
                .errorMessage(e.getErrorCode().getMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ExceptionHandler(CustomClientException.class)
    public ResponseEntity<ApiResponse<Object>> clientException(CustomClientException e) {
        ApiResponse<Object> body = ApiResponse.of(e.getStatus(), e.getBody());
        return ResponseEntity.status(e.getStatus()).body(body);
    }

    @ExceptionHandlerLog
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    ApiResponse<Object> notFoundException(CustomNotFoundException e) {
        return ApiResponse.of(
            NOT_FOUND,
            ErrorResponse.builder()
                .errorCode(e.getErrorCode().getCode())
                .errorMessage(e.getErrorCode().getMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ResponseStatus(BAD_GATEWAY)
    @ExceptionHandler(ResourceAccessException.class)
    ApiResponse<Object> serverError(ResourceAccessException e) {
        return ApiResponse.of(
            BAD_GATEWAY,
            ErrorResponse.builder()
                .errorCode(CLIENT_CONNECTION_REFUSED.getCode())
                .errorMessage(CLIENT_CONNECTION_REFUSED.getMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ApiResponse<Object> serverError(Exception e) {
        return ApiResponse.of(
            INTERNAL_SERVER_ERROR,
            ErrorResponse.builder()
                .errorCode(INTERNAL_SERVER_ERROR.value())
                .errorMessage(e.getMessage())
                .build()
        );
    }

    @ExceptionHandlerLog
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomBusinessException.class)
    ApiResponse<Object> customBusinessException(CustomBusinessException e) {
        return ApiResponse.of(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorResponse.builder()
                .errorCode(e.getErrorCode().getCode())
                .errorMessage(e.getErrorCode().getMessage())
                .build()
        );
    }
}
