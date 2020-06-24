package org.feasy.cloud.redis.client.impl;

import org.feasy.cloud.redis.client.BaseRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redis基础通用操作类
 * @author yangxiaohui
 */
@Slf4j
@SuppressWarnings("all")
public class BaseRedisServiceImpl implements BaseRedisService {
    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;
    /**
     * 设置缓存有效时间 0 永久有效
     * @param key   key
     * @param expire    有效时间 单位-秒
     * @return true:成功 false：失败
     */
    @Override
    public boolean setExpire(String key, long expire){
        try {
            redisTemplate.expire(key, expire, TimeUnit.MILLISECONDS);
        }catch (Exception e){
            log.error("设置缓存失效时间失败 Key：【{}】异常：{}",key,e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 获取缓存剩余有效时间
     * @param key Key
     * @return  -1 代表未找到Key 0 代表永久有效
     */
    @Override
    public long getExpire(String key){
        if (key!=null){
            Long value=redisTemplate.getExpire(key);
            if (value!=null){
                return value;
            }else {
                log.warn("Key:【{}】缓存未击中...",key);
            }
        }
        return -1;
    }

    /**
     * 根据Key删除缓存
     * @param key   key
     * @return  true 成功 false 失败
     */
    @Override
    public boolean remove(String key){
        boolean result=false;
        if (key!=null){
            try {
                result=redisTemplate.delete(key);
            }catch (Exception e){
                log.error("Key:【{}】缓存删除失败...{}",key,e.getMessage());
                return false;
            }
        }
        return result;
    }
}
