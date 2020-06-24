package org.feasy.cloud.common.result;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Response返回结果类
 * @author yangxiaohui
 */
@Slf4j
@Data
public class Result<T> {
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 请求状态消息
     */
    private String msg;
    /**
     * 时间戳
     */
    private LocalDateTime timestamp;
    /**
     * 返回的数据 相应数据
     */
    private Object data;
    Result(){
        this.code=ResultEnum.SUCCESS.code();
        this.msg=ResultEnum.SUCCESS.msg();
        this.timestamp= LocalDateTime.now();
        this.data=createNoneData();
    }
    Result(T data){
        this.code=ResultEnum.SUCCESS.code();
        this.msg=ResultEnum.SUCCESS.msg();
        this.timestamp= LocalDateTime.now();
        this.data=data;
    }
    Result(ResultEnum resultEnum){
        this.code=resultEnum.code();
        this.msg=resultEnum.msg();
        this.timestamp= LocalDateTime.now();
        this.data=createNoneData();
    }
    Result(ResultEnum resultEnum,T data){
        this.code=resultEnum.code();
        this.msg=resultEnum.msg();
        this.timestamp= LocalDateTime.now();
        this.data=data;
    }
    /**
     * 创建一个空 {@link JSONObject} 对象
     * @return 空 {@link JSONObject} 对象
     */
    private static Object createNoneData(){
        return new JSONObject();
    }
}
