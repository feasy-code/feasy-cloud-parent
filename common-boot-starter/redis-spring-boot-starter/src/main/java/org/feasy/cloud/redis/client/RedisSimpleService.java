package org.feasy.cloud.redis.client;

/**
 * Redis的简单对象数据类型相关操作
 * opsForXXX和boundXXXOps的区别：前者获取一个operator，但是没有指定操作的对象（key），可以在一个连接（事务）内操作多个key以及对应的value；后者获取了一个指定操作对象（key）的operator，在一个连接（事务）内只能操作这个key对应的value。
 * @author yangxiaohui
 */
public interface RedisSimpleService extends BaseRedisService {
    /**
     * 普通缓存数据
     * @param key   key
     * @param value 值
     * @return true:成功 false：失败
     */
    boolean put(String key, Object value);
    /**
     * 普通缓存数据
     * @param key  key
     * @param value 值
     * @param expire 有效期  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return  true:成功 false：失败
     */
    boolean put(String key, Object value, long expire);
    /**
     * 获取缓存结果
     * @param key Key
     * @return 缓存结果或者空
     */
    <T> T get(String key);
}
