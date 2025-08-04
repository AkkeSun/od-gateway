package com.example.odgateway.adapter.out.persistence.redis;

import static com.example.odgateway.infrastructure.util.JsonUtil.parseJson;
import static com.example.odgateway.infrastructure.util.JsonUtil.parseJsonList;

import com.example.odgateway.application.port.out.RedisStoragePort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisStorageAdapter implements RedisStoragePort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @CircuitBreaker(name = "redis", fallbackMethod = "findDataFallback")
    public <T> T findData(String key, Class<T> clazz) {
        String redisData = redisTemplate.opsForValue().get(key);
        if (!StringUtils.hasText(redisData)) {
            return null;
        }
        return parseJson(redisData, clazz);
    }

    @Override
    @CircuitBreaker(name = "redis", fallbackMethod = "findDataListFallback")
    public <T> List<T> findDataList(String key, Class<T> clazz) {
        String redisData = redisTemplate.opsForValue().get(key);
        if (!StringUtils.hasText(redisData)) {
            return Collections.emptyList();
        }
        return parseJsonList(redisData, clazz);
    }

    @Override
    @CircuitBreaker(name = "redis", fallbackMethod = "registerRedisFallback")
    public void register(String key, String data, long ttl) {
        redisTemplate.opsForValue().set(key, data, ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    @CircuitBreaker(name = "redis", fallbackMethod = "deleteRedisFallback")
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    private <T> T findDataFallback(String key, Class<T> clazz, Throwable throwable) {
        log.error("[redisFallback] open");
        return null;
    }

    private <T> List<T> findDataListFallback(String key, Class<T> clazz, Throwable throwable) {
        return Collections.emptyList();
    }

    private void registerRedisFallback(String key, String data, long ttl, Throwable throwable) {
    }

    private void deleteRedisFallback(String key, Throwable throwable) {
    }

}
