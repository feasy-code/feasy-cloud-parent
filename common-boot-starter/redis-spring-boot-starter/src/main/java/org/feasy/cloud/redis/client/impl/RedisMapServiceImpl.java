package org.feasy.cloud.redis.client.impl;

import org.feasy.cloud.redis.client.RedisMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author yangxiaohui
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RedisMapServiceImpl extends BaseRedisServiceImpl implements RedisMapService {

    @Override
    public void put(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key,hashKey,value);
    }


    @Override
    public <T> T get(String key, String hashKey) {
        Object result=redisTemplate.opsForHash().get(key,hashKey);
        if (result==null){
            return null;
        }
        return (T)result;
    }

    @Override
    public void removeByHashKey(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key,hashKey);
    }
}
