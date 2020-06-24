package org.feasy.cloud.common.util;

import java.util.regex.Pattern;

/**
 * <p>
 *      正则验证工具类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/6
 */
public class RegularVerifyUtil {

    /**
     * 校验IP字符串合法性
     * @param ipStr IP字符串
     */
    public static boolean verifyIP_Str(String ipStr){
        String ipReg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern ipPattern = Pattern.compile(ipReg);
        return ipPattern.matcher(ipStr).matches();
    }

    public static boolean verifyPhoneNumbers(String... phoneNumbers){
        String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";
        if (phoneNumbers.length<=0){
            return false;
        }
        for (String phoneNumber:phoneNumbers){
            if(!Pattern.matches(REGEX_MOBILE, phoneNumber)){
                return false;
            }
        }
        return true;
    }
}
