package org.feasy.cloud.redis.client;

import java.util.Set;

/**
 * Redis的Set数据类型相关操作 无序、不可重复
 * @author yangxiaohui
 */
public interface RedisSetService extends BaseRedisService {
    /**
     * Set添加元素
     * @param key   key
     * @param value 值
     * @return true 成功 false失败
     */
    boolean put(String key, Object value);
    /**
     * Set添加元素并设置失效时间
     * @param key   key
     * @param value 值的集和
     * @param expire 时间 单位-秒
     * @return  true成功 false失败
     */
    boolean put(String key, Object value, long expire);
    /**
     * Set添加多个元素
     * @param key   key
     * @param value 值的集和
     * @return  true成功 false失败
     */
    boolean putArray(String key, Object[] value);

    /**
     * Set添加多个元素并设置失效时间
     * @param key   key
     * @param value 值的集和
     * @param expire 时间 单位-秒
     * @return  true成功 false失败
     */
    boolean putArray(String key, Object[] value, long expire);

    /**
     * 删除Set中的指定值
     * @param key   key
     * @param value 指定值
     * @return  true成功 false失败
     */
    boolean remove(String key, Object value);

    /**
     * 判断集和是否存在该元素
     * @param key   key
     * @param value 值
     * @return  true存在 false不存在
     */
    boolean contains(String key, Object value);

    /**
     * 获取指定Set的所有数据
     * @param key key
     * @return  Set集和
     */
    <T> Set<T> allData(String key);
    /**
     * 返回多个集合的并集  sunion
     *
     * @param key1 key1
     * @param key2 key2
     * @return 并集Set
     */
    <T> Set<T> union(String key1, String key2);
    /**
     * 返回多个集合的交集 sinter
     * @param key1 key1
     * @param key2 key2
     * @return 交集Set
     */
    <T> Set<T> intersect(String key1, String key2);

    /**
     * 返回集合key1中存在，但是key2中不存在的数据集合  sdiff 异集-差集
     * @param key1 key1
     * @param key2 key2
     * @return 异集-差集Set
     */
    <T> Set<T> diff(String key1, String key2);
}
