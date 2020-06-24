package org.feasy.cloud.common.constant;

/**
 * <p>
 * 常量类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/12
 */
public class Constant {
    /**
     * 请求Token内的用户ID 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_USER_ID_KEY = "SmUserId";
    /**
     * 请求Token内的用户名称 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_USER_NAME_KEY = "SmUserName";
    /**
     * 请求Token内的用户手机号 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_USER_PHONE_KEY = "SmUserPhone";
    /**
     * 请求Token内的AppID 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_ACCOUNT_ID_KEY = "SmAccountId";
    /**
     * 请求Token内的APP名称 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_ACCOUNT_NAME_KEY = "SmAccountName";
    /**
     * 请求Token内的私钥名称 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_TOKEN_ACCOUNT_SECRET_KEY_KEY = "SmAccountSecretKey";
    /**
     * 请求内的ClientType(请求来源客户端类型) 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_CLIENT_TYPE_KEY = "ClientType";
    /**
     * 请求Token 存放在Header的Key值
     */
    public static final String DEFAULT_REQUEST_AUTHORIZATION_KEY = "Authorization";
    /**
     * 存放用户Token的前缀
     */
    public static final String REDIS_USER_TOKEN_KEY_PREFIX = "SmUser:Token:";
    /**
     * 存放第三方账户Token的前缀
     */
    public static final String REDIS_ACCOUNT_TOKEN_KEY= "SmAccount:Token:";
    /**
     * 存放Redis内的常规服务ApiMap的Key
     */
    public static final String REDIS_SM_SERVER_API_KEY = "SmServerApi:ServerApiMap";
    /**
     * 存放Redis内的PathValue服务ApiMap的Key
     */
    public static final String REDIS_SM_SERVER_PATH_VALUE_API_KEY= "SmServerApi:PathValueServerApiMap";


}
