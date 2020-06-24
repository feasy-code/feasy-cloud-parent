package org.feasy.cloud.redis.client;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * Redis的ZSet数据类型相关操作
 * ZSet就相当于一个有了排序功能和下标的Set集和，算是Set的增强版
 * @author yangxiaohui
 */
public interface RedisZSetService extends BaseRedisService {
    /**
     * 新增一个元素到Set
     * @param key   key
     * @param value 值
     * @param score score，排序会用到
     * @return true成功 false失败
     */
    boolean put(String key, Object value, double score);

    /**
     * 新增一个元素到Set并设置Set的失效时间
     * @param key   key
     * @param value 值
     * @param score score，排序会用到
     * @param expire   时间
     * @return  true 成功 false失败
     */
    boolean put(String key, Object value, double score, long expire);

    /**
     * 删除元素
     * @param key   key
     * @param value 要删除的值
     */
    void remove(String key, String value);
    /**
     * score的增加or减少 zincrby，如果value元素不存在 会新增一个元素
     * @param key key
     * @param value 值
     * @param score score值
     * @return score值
     */
    Double incrScore(String key, String value, double score);
    /**
     * 查询value对应的score   zscore
     * 当value在集合中时，返回其score；如果不在，则返回null
     * @param key key
     * @param value value
     * @return score
     */
    Double score(String key, String value);
    /**
     * 判断value在zset中的排名  zrank
     * @param key   key
     * @param value value
     * @return 排序号
     */
    Long rank(String key, String value);
    /**
     * 查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     * 返回有序的集合，score小的在前面
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> range(String key, int start, int end);
    /**
     * 查询集合中指定顺序的值和score，0, -1 表示获取全部的集合内容
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScore(String key, int start, int end) ;
    /**
     * 查询集合中指定顺序的值  zrevrange
     * 返回有序的集合中，score大的在前面
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Object> revRange(String key, int start, int end) ;
    /**
     * 根据score的值，来获取满足条件的集合  zrangebyscore
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> sortRange(String key, int min, int max) ;

}
