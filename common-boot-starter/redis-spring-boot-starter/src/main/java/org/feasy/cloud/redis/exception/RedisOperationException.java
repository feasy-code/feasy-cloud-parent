package org.feasy.cloud.redis.exception;

/**
 * Redis操作异常类
 * @author yangxiaohui
 */
public class RedisOperationException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public RedisOperationException(){
        super();
    }
    public RedisOperationException(String msg){
        super(msg);
    }
}
