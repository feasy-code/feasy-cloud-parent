package org.feasy.cloud.redis.client;


import org.feasy.cloud.redis.util.RedisPage;

import java.util.List;

/**
 * Redis的List数据类型相关操作
 * @author yangxiaohui
 */
public interface RedisListService extends BaseRedisService {
    /**
     * 从List左侧添加数据
     * @param key   key
     * @param value 值
     * @return  true 成功 false 失败
     */
    boolean putListLeft(String key, Object value);
    boolean putListLeft(String key, Object value, long expire);
    /**
     * 从List左侧添加多条数据
     * @param key   key
     * @param value 值
     * @return  true 成功 false 失败
     */
    boolean putListLeftArray(String key, Object[] value);
    boolean putListLeftArray(String key, Object[] value, long expire);
    /**
     * 从List右侧添加数据
     * @param key   key
     * @param value 值
     * @return  true 成功 false 失败
     */
    boolean putListRight(String key, Object value);
    boolean putListRight(String key, Object value, long expire);

    /**
     * 从List右侧添加多条数据
     * @param key   key
     * @param value 值
     * @return  true 成功 false 失败
     */
    boolean putListRightArray(String key, Object[] value);
    boolean putListRightArray(String key, Object[] value, long expire);

    /**
     * 阻塞式取出并移除（消费）最左边的一个数据
     * @param key key
     * @return 返回数据
     */
    <T> T popListLeft(String key);
    /**
     * 阻塞式取出并移除（消费）最右边的一个数据
     * @param key key
     * @return 返回数据
     */
    <T> T popListRight(String key);
    /**
     * 获取List分页数据
     * @param key   key
     * @param pageNum   页码
     * @param pageSize  每页几条
     * @return  数据集合
     */
    <T> RedisPage<T> pageList(String key, long pageNum, long pageSize);
    /**
     * 查询List所有数据
     * @param key  key
     * @return  List结果集
     */
    <T> List<T> list(String key);
    /**
     * 获取List大小
     * @param key   key
     * @return  大小
     */
    long count(String key);

    /**
     * 根据下标获取数据
     * @param key   key
     * @param index 下标
     * @return 获取结果
     */
    <T> T getByIndex(String key, long index);

    /**
     * 根据下标删除数据，可以传多个下标
     * @param key   key
     * @param index     下标
     * @return  true成功 false失败
     */
    boolean removeByIndex(String key, long... index);

}
