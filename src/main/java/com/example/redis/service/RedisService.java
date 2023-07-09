package com.example.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;

@Service
public class RedisService {

    private final int LIMIT_TIME = 3 * 60;

    @Autowired
    private StringRedisTemplate StringRedisTemplate;

    public void setRedisValue(String key, String value) {
        ValueOperations<String, String> stringValueOperations = StringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        //stringValueOperations.set(key, value, LIMIT_TIME);
    }

    public String getRedisValue(String key) {
        ValueOperations<String, String> stringValueOperations = StringRedisTemplate.opsForValue();
        String value = stringValueOperations.get(key);
        if(value == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        };

        return value;
    }

    public void updateRedisValues(String key, String value) {
        ValueOperations<String, String> stringValueOperations = StringRedisTemplate.opsForValue();
        stringValueOperations.getAndSet(key, value);
    }

    public void deleteRedisValue(String key) {
        StringRedisTemplate.delete(key);
    }
    
}
