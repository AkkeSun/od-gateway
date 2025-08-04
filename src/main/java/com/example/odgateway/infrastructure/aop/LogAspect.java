package com.example.odgateway.infrastructure.aop;

import static com.example.odgateway.infrastructure.util.JsonUtil.toObjectNode;

import com.example.odgateway.application.port.out.ApiCallLogStoragePort;
import com.example.odgateway.domain.model.ApiCallLog;
import com.example.odgateway.infrastructure.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final JwtUtil jwtUtil;
    private final HttpServletRequest request;
    private final ApiCallLogStoragePort apiCallLogStoragePort;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    private void controllerMethods() {
    }

    @Pointcut("@annotation(com.example.odgateway.infrastructure.aop.ExceptionHandlerLog))")
    private void controllerAdviceMethods() {
    }

    @Around("controllerMethods()")
    public Object controllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String httpMethod = request.getMethod();
        String path = request.getRequestURI();

        ObjectNode requestInfo = new ObjectMapper().createObjectNode();
        ObjectNode requestParam = new ObjectMapper().createObjectNode();
        ObjectNode requestBody = new ObjectMapper().createObjectNode();
        ObjectNode account = new ObjectMapper().createObjectNode();

        // request parameter
        request.getParameterMap().forEach((key, value) -> {
            requestParam.put(key, String.join(",", value));
        });

        // request body
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) {
                continue;
            }
            if (arg.toString().contains("{")) {
                requestBody = toObjectNode(arg);
            }
        }

        // account info
        String token = request.getHeader("Authorization");
        if (token != null) {
            account = jwtUtil.getAccountInfo(token);
        }

        requestInfo.put("param", requestParam);
        requestInfo.put("body", requestBody);
        requestInfo.put("account", account);

        apiCallLogStoragePort.register(ApiCallLog.of(request, account));
        log.info("[{} {}] request - {}", httpMethod, path, requestInfo);
        Object result = joinPoint.proceed();
        log.info("[{} {}] response - {}", httpMethod, path, result);

        return result;
    }

    @Around("controllerAdviceMethods()")
    public Object controllerAdviceLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        log.info("[{} {}] response - {}", request.getMethod(), request.getRequestURI(), result);
        return result;
    }
}
