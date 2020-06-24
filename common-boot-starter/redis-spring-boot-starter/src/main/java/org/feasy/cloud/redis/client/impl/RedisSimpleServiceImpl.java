package org.feasy.cloud.redis.client.impl;

import org.feasy.cloud.redis.client.RedisSimpleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 简单字符串Redis操作类
 * @author yangxiaohui
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RedisSimpleServiceImpl extends BaseRedisServiceImpl implements RedisSimpleService {
    @Override
    public boolean put(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, value,e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public boolean put(String key, Object value, long expire ){
        try {
            redisTemplate.opsForValue().set(key,value,expire, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, value,e.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public <T> T get(String key){
        if (StringUtils.isNotBlank(key)){
            Object value=redisTemplate.opsForValue().get(key);
            if (value!=null){
                return(T)value;
            }else {
                log.error("Key:【{}】缓存未击中...",key);
            }
        }
        return null;
    }


}
