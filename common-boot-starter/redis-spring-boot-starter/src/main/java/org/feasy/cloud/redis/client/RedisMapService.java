package org.feasy.cloud.redis.client;

/**
 * Redis的Map|Hash数据类型相关操作
 * @author yangxiaohui
 */
public interface RedisMapService extends BaseRedisService {
    /**
     * 增加一个数据
     * @param key   key
     * @param hashKey hash的key/field
     * @param value 值
     */
    void put(String key, String hashKey, String value);

    /**
     * 获取指定值
     * @param key   key
     * @param hashKey hash的field
     * @return 数据
     */
    <T> T get(String key, String hashKey);

    /**
     * 删除指定的元素
     * @param key   key
     * @param hashKey   hashKey
     */
    void removeByHashKey(String key, String hashKey);
}
