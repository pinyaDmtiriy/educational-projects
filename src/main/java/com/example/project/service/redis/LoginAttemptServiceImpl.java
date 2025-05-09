package com.example.project.service.redis;

import com.example.project.exception.ex.BANNED;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class LoginAttemptServiceImpl {

    private final Long MAX_ATTEMPT = 10L;
    private final Duration TTL = Duration.ofMinutes(10);

    private RedisTemplate<String, Long> redisTemplate;

    public LoginAttemptServiceImpl(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void checkNumberOfLoginAttempts(String ip) {
            Long CURRENT_ATTEMPT = redisTemplate.opsForValue().increment(ip);

            if(CURRENT_ATTEMPT == 1L) {
                redisTemplate.expire(ip,TTL);
            }
            if(CURRENT_ATTEMPT >= MAX_ATTEMPT) {
                throw new BANNED("Ban");
            }
    }

}
