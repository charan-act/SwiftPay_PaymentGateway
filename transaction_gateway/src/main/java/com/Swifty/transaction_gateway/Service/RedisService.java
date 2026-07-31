package com.Swifty.transaction_gateway.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration IDEMPOTENCY_EXPIRY = Duration.ofHours(24);

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void saveKey(String key) {
        redisTemplate.opsForValue().set(key, "PROCESSED", IDEMPOTENCY_EXPIRY);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}