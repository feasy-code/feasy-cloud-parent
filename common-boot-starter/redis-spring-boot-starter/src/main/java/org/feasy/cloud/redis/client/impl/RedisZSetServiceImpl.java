package org.feasy.cloud.redis.client.impl;

import org.feasy.cloud.redis.client.RedisZSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author yangxiaohui
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RedisZSetServiceImpl extends BaseRedisServiceImpl implements RedisZSetService {
    @Override
    public boolean put(String key, Object value, double score) {
        Boolean result = redisTemplate.opsForZSet().add(key,value,score);
        return null==result?false:result;
    }

    @Override
    public boolean put(String key, Object value, double score, long expire) {
        Boolean result = redisTemplate.opsForZSet().add(key,value,score);
        this.setExpire(key,expire);
        return null==result?false:result;
    }

    @Override
    public void remove(String key, String value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    @Override
    public Double incrScore(String key, String value, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    @Override
    public Double score(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Long rank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }
    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     *
     * 返回有序的集合，score小的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Object> range(String key, int start, int end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScore(String key, int start, int end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 查询集合中指定顺序的值  zrevrange
     *
     * 返回有序的集合中，score大的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Object> revRange(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 根据score的值，来获取满足条件的集合  zrangebyscore
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    @Override
    public Set<Object> sortRange(String key, int min, int max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }


}
