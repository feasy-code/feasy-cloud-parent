package org.feasy.cloud.common.util;

import java.util.UUID;

/**
 * UUID工具类
 * @author yangxiaohui
 */
public class UUIDUtil {
    public static String buildUUID_32Upper(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
    public static String buildUUID_32Lower(){
        return UUID.randomUUID().toString().replace("-","").toLowerCase();
    }
}
