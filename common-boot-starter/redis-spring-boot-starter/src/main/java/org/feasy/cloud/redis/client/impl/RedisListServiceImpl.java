package org.feasy.cloud.redis.client.impl;

import com.alibaba.fastjson.JSONObject;
import org.feasy.cloud.redis.client.RedisListService;
import org.feasy.cloud.redis.exception.RedisOperationException;
import org.feasy.cloud.redis.util.RedisObjectUtil;
import org.feasy.cloud.redis.util.RedisPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangxiaohui
 */
@Slf4j
@Component
@SuppressWarnings("unchecked")
public class RedisListServiceImpl extends BaseRedisServiceImpl implements RedisListService {

    @Override
    public boolean putListLeft(String key,Object value){
        Long result=redisTemplate.opsForList().leftPush(key, value);
        return (null==result?-1:result)>=0;
    }
    @Override
    public boolean putListLeft(String key,Object value,long expire){
        BoundListOperations<String,Object> boundListOperations =redisTemplate.boundListOps(key);
        boundListOperations.expire(expire,TimeUnit.MILLISECONDS);
        Long result=boundListOperations.leftPush(value);
        return (null==result?-1:result)>=0;
    }
    @Override
    public boolean putListRight(String key,Object value){
        BoundListOperations<String,Object> boundListOperations =redisTemplate.boundListOps(key);
        Long result=boundListOperations.rightPush(value);
        return (null==result?-1:result)>=0;
    }
    @Override
    public boolean putListRight(String key,Object value,long expire){
        BoundListOperations<String,Object> boundListOperations =redisTemplate.boundListOps(key);
        boundListOperations.expire(expire,TimeUnit.MILLISECONDS);
        Long result=boundListOperations.rightPush(value);
        return (null==result?-1:result)>=0;
    }

    @Override
    public boolean putListLeftArray(String key, Object[] value) {
        try {
            redisTemplate.opsForList().leftPushAll(key,value);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, JSONObject.toJSONString(value),e);
            return false;
        }
        return true;
    }

    @Override
    public boolean putListLeftArray(String key, Object[] value,long expire) {
        try {
           BoundListOperations<String,Object> boundListOperations=redisTemplate.boundListOps(key);
           boundListOperations.leftPushAll(value);
           boundListOperations.expire(expire,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, JSONObject.toJSONString(value),e);
            return false;
        }
        return true;
    }
    @Override
    public boolean putListRightArray(String key, Object[] value) {
        try {
            redisTemplate.opsForList().rightPushAll(key,value);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, JSONObject.toJSONString(value),e);
            return false;
        }
        return true;
    }

    @Override
    public boolean putListRightArray(String key, Object[] value,long expire) {
        try {
            BoundListOperations<String,Object> boundListOperations=redisTemplate.boundListOps(key);
            boundListOperations.rightPushAll(value);
            boundListOperations.expire(expire,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            log.error("新增缓存失败：Key:{},Value:{},Msg:{}",key, JSONObject.toJSONString(value),e);
            return false;
        }
        return true;
    }


    @Override
    public <T> T popListLeft(String key){
        try {
            Object object=redisTemplate.opsForList().leftPop(key);
            if (object==null){
                return null;
            }
            return (T)object;
        }catch (Exception e){
            log.error("获取缓存失败Key：【{}】,{}",key,e);
            return null;
        }
    }
    @Override
    public <T> T popListRight(String key){
        try {
            Object object=redisTemplate.opsForList().rightPop(key);
            if (object==null){
                return null;
            }
            return (T)object;
        }catch (Exception e){
            log.error("获取缓存失败Key：【{}】,{}",key,e);
            return null;
        }
    }
    @Override
    public <T> RedisPage<T> pageList(String key, long pageNum, long pageSize){
        ListOperations<String,Object> listOperations= redisTemplate.opsForList();
        // 总数据条数
        Long count=listOperations.size(key);
        if (null==count||count<=0){
            log.error("缓存未击中Key:【{}】",key);
            return new RedisPage<>();
        }
        RedisPage<T> page = new RedisPage<>(pageNum,pageSize,count);
        List<Object> list=listOperations.range(key, page.getStart(), page.getEnd());
        if (null ==list){
            return new RedisPage<>();
        }
        page.setData(RedisObjectUtil.listObjectToList(list));
        return page;
    }
    @Override
    public <T> List<T> list(String key){
        ListOperations<String,Object> listOperations= redisTemplate.opsForList();
        Long count=listOperations.size(key);
        if (null==count||count<=0){
            log.error("缓存未击中Key:【{}】",key);
            return new ArrayList<>();
        }
        List<Object> list=listOperations.range(key, 0, count - 1);
        if (null!=list){
            return RedisObjectUtil.listObjectToList(list);
        }
        return new ArrayList<>();
    }


    @Override
    public long count(String key) {
        Long size=redisTemplate.opsForList().size(key);
        return null==size?0:size;
    }

    @Override
    public <T> T getByIndex(String key, long index) {
        if (index<0){
            throw new RedisOperationException("无效的下标【"+index+"】！");
        }
        Object o=redisTemplate.opsForList().index(key,index);
        return (T)o;
    }

    @Override
    public boolean removeByIndex(String key, long... index) {
        try {
            ListOperations<String,Object> listOperations=redisTemplate.opsForList();
            for (long i:index){
                if (i>=0) {
                    listOperations.remove(key, i, getByIndex(key, i));
                }
            }
        }catch (Exception e){
            log.error("缓存删除失败【{}】【{}】:{}",key, JSONObject.toJSONString(index),e);
            return false;
        }
        return true;
    }

}
