package org.feasy.cloud.redis.client.impl;

import org.feasy.cloud.redis.client.RedisSetService;
import org.feasy.cloud.redis.util.RedisObjectUtil;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangxiaohui
 */
@Component
@SuppressWarnings("unchecked")
public class RedisSetServiceImpl extends BaseRedisServiceImpl implements RedisSetService {


    @Override
    public boolean put(String key, Object value) {
        Long result = redisTemplate.opsForSet().add(key, value);
        return (null == result ? -1 : result) >= 0;
    }

    @Override
    public boolean put(String key, Object value, long expire) {
        Long result = redisTemplate.opsForSet().add(key, value);
        this.setExpire(key, expire);
        return (null == result ? -1 : result) >= 0;
    }

    @Override
    public boolean putArray(String key, Object[] value) {
        for (Object o : value) {
            SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
            Long result = setOperations.add(key, o);
            if (!((null == result ? -1 : result) >= 0)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean putArray(String key, Object[] value, long expire) {
        if (!this.putArray(key, value)) {
            return false;
        }
        this.setExpire(key, expire);
        return true;
    }

    @Override
    public boolean remove(String key, Object value) {
        Long result = redisTemplate.opsForSet().remove(key, value);
        return (null == result ? -1 : result) >= 0;
    }

    @Override
    public boolean contains(String key, Object value) {
        Boolean result = redisTemplate.opsForSet().isMember(key, value);
        return null == result ? false : result;
    }

    @Override
    public <T> Set<T> allData(String key) {
        Set<Object> result= redisTemplate.opsForSet().members(key);
        return RedisObjectUtil.setObjectToSet(null==result?new HashSet<>():result);
    }

    @Override
    public <T> Set<T> union(String key1, String key2) {
        Set<Object> result=redisTemplate.opsForSet().union(key1, key2);
        return RedisObjectUtil.setObjectToSet(null==result?new HashSet<>():result);
    }

    @Override
    public <T> Set<T> intersect(String key1, String key2) {
        Set<Object> result=redisTemplate.opsForSet().intersect(key1, key2);
        return RedisObjectUtil.setObjectToSet(null==result?new HashSet<>():result);
    }

    @Override
    public <T> Set<T> diff(String key1, String key2) {
        Set<Object> result=redisTemplate.opsForSet().difference(key1, key2);
        return RedisObjectUtil.setObjectToSet(null==result?new HashSet<>():result);
    }

}
