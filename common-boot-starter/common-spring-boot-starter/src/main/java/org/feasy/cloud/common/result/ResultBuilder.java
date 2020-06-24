package org.feasy.cloud.common.result;

import com.alibaba.fastjson.JSONObject;

/**
 * 请求响应体 {@link Result} 构建工具类 
 * @author yangxiaohui
 */
public class ResultBuilder<T> {

    /**
     * 创建一个默认请求成功响应体 
     *     <p>
     *         默认成功200状态码 {@link ResultEnum}
     *         默认响应内容为空的 {@link JSONObject}
     *     </p>
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> success(){
        return new Result<>();
    }

    /**
     * 创建一个默认请求成功响应体并返回指定响应结果 
     *     <p>
     *         默认成功200状态码 {@link ResultEnum}
     *     </p>
     * @param data 响应内容
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }
    /**
     * 创建一个请求成功响应体并指定状态码 
     *     <p>
     *         默认响应内容为空的 {@link JSONObject}
     *     </p>
     * @param resultEnum 响应状态码枚举
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> success(ResultEnum resultEnum){
        return new Result<>(resultEnum);
    }
    /**
     * 创建一个请求成功响应体并指定状态码和响应内容 
     * @param resultEnum    指定响应状态码枚举
     * @param data          指定响应消息结果
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> success(ResultEnum resultEnum,T data){
        return new Result<>(resultEnum,data);
    }
    /**
     * 创建一个默认请求失败响应体 - 未知错误 
     *     <p>
     *         默认失败-1状态码 {@link ResultEnum}
     *         默认响应内容为空的 {@link JSONObject}
     *     </p>
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> error(){
        return new Result<>(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 创建一个默认请求失败响应体 - 未知错误 
     *     <p>
     *         默认响应内容为空的 {@link JSONObject}
     *     </p>
     * @param data 响应内容
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> error(T data){
        return new Result<>(ResultEnum.UNKNOWN_ERROR,data);
    }
    /**
     *  创建一个指定请求失败状态码的响应体 
     *     <p>
     *         默认响应内容为空的 {@link JSONObject}
     *     </p>
     * @param resultEnum 响应状态码枚举
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> error(ResultEnum resultEnum){
        return new Result<>(resultEnum);
    }
    /**
     *  创建一个指定请求失败状态码并且指定响应内容的响应体 
     * @param resultEnum 响应状态码枚举
     * @param data 响应内容
     * @return 一个 {@link Result} 响应体
     */
    public static <T> Result<T> error(ResultEnum resultEnum,T data){
        return new Result<>(resultEnum,data);
    }

}
