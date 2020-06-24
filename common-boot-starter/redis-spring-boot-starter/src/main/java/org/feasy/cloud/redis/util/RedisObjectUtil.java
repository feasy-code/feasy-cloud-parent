package org.feasy.cloud.redis.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Redis对象处理工具类
 * @author yangxiaohui
 */
@SuppressWarnings("unchecked")
public class RedisObjectUtil {
    /**
     * Object数组转List
     * @param objects 对象数组
     * @param <T> 泛型
     * @return  List
     */
    public static <T> List<T> arrayObjectToList(Object[] objects)throws ClassCastException{
        return new ArrayList<T>(){{
            for (Object t:objects){
                if (null!=t) {
                    add((T) t);
                }
            }
        }};
    }
    public static <T> List<T> listObjectToList(List<Object> objects)throws ClassCastException{
        return new ArrayList<T>(){{
            for (Object t:objects){
                if (null!=t) {
                    add((T) t);
                }
            }
        }};
    }
    public static <T> Set<T> setObjectToSet(Set<Object> objects)throws ClassCastException{
        return new HashSet<T>(){{
            for (Object t:objects){
                if (null!=t) {
                    add((T) t);
                }
            }
        }};
    }
}
