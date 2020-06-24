package org.feasy.cloud.redis.client;

/**
 * @author yangxiaohui
 */
public interface BaseRedisService {
    /**
     * 设置缓存有效时间 0 永久有效
     * @param key   key
     * @param expire    有效时间 单位-秒
     * @return true:成功 false：失败
     */
    boolean setExpire(String key, long expire);

    /**
     * 获取缓存剩余有效时间
     * @param key Key
     * @return  -1 代表未找到Key 0 代表永久有效
     */
    long getExpire(String key);
    /**
     * 根据Key删除缓存
     * @param key   key
     * @return  true 成功 false 失败
     */
    boolean remove(String key);
}
