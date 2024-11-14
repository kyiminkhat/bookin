package com.booking.api.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

@Service
public class RedisCacheService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CACHE_KEY = "class_slot_availability:";

    public boolean isClassFull(Long classId) {
        String cacheKey = CACHE_KEY + classId;
        String value = redisTemplate.opsForValue().get(cacheKey);
        if (value != null && Integer.parseInt(value) >= 5) { // Assuming max 5 slots for simplicity
            return true;
        }
        return false;
    }

    public void reserveSlot(Long classId) {
        String cacheKey = CACHE_KEY + classId;
        redisTemplate.opsForValue().increment(cacheKey, 1);
    }

    public void releaseSlot(Long classId) {
        String cacheKey = CACHE_KEY + classId;
        redisTemplate.opsForValue().decrement(cacheKey, 1);
    }
}
