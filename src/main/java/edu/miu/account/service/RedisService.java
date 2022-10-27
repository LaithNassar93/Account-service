package edu.miu.account.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.account.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper mapper;

    public boolean isExist(String key) {
        return Objects.nonNull(redisTemplate.opsForValue().get(key));
    }

    public void setValue(final String key, Account account) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(account));
            redisTemplate.expire(key, 2, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Account getValue(final String key) {
        try {
            return mapper.readValue(redisTemplate.opsForValue().get(key), Account.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteByKey(String key) {
        redisTemplate.delete(key);
    }
}
