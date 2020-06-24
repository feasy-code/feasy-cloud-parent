package org.feasy.cloud.permission.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.feasy.cloud.permission.api.annotation.VerifyPermission;
import org.feasy.cloud.permission.api.entity.ApiDetail;
import org.feasy.cloud.permission.api.entity.ServerApis;
import org.feasy.cloud.permission.api.properties.ServerInterfacePublishProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 *  服务APIs 构造工具类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Slf4j
public class ServerApisBuilder {

    public static ServerApis builderServerApis(WebApplicationContext applicationContext, ServerInterfacePublishProperties publishProperties){
        // 初始化类 ServerApis
        ServerApis serverApis = ServerApisBuilder.buildServerApis(publishProperties);
        // 初始化服务接口
        Set<ApiDetail> apiDetailSet = buildApiDetailArray(applicationContext,publishProperties);
        serverApis.setApis(new ArrayList<>(apiDetailSet));
        // 设置版本号
        serverApis.setServerApiVersion(DigestUtils.md5Hex(JSONArray.toJSONString(apiDetailSet)));
        log.info("Local Apis Init...  {}", JSONObject.toJSONString(serverApis));
        return serverApis;
    }



    private static Set<ApiDetail> buildApiDetailArray(WebApplicationContext applicationContext, ServerInterfacePublishProperties publishProperties){
        Set<ApiDetail> apiDetailSet=new TreeSet<>();
        // 取出所有的Mapping
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        // 遍历服务接口信息，筛选符合条件的数据
        map.forEach((mappingInfo,handlerMethod) ->{
            // 包路径
            String classPackage = handlerMethod.getBeanType().getName();
            if (verifyClassPackageHasProperties(classPackage,publishProperties.getInterfacePackage())){
                // 构建实体
                ApiDetail apiDetail = new ApiDetail();
                // 方法
                Method method = handlerMethod.getMethod();
                // 获取方法请求类型
                Object[] methodTypes = mappingInfo.getMethodsCondition().getMethods().toArray();
                // 获取方法路径
                Object[] methodPaths = mappingInfo.getPatternsCondition().getPatterns().toArray();
                // 判断是否包含权限标识注解
                if (method.isAnnotationPresent(VerifyPermission.class)) {
                    VerifyPermission verifyPermission = method.getAnnotation(VerifyPermission.class);
                    if (null != verifyPermission && verifyPermission.keys().length > 0) {
                        apiDetail.setPermissionKeys(StringUtils.join(verifyPermission.keys(), ","));
                    }
                    if (StringUtils.isNotBlank(verifyPermission.name())){
                        apiDetail.setApiName(verifyPermission.name());
                    }
                }
                // 完善信息
                apiDetail.setApiName(methodPaths[0].toString());
                apiDetail.setApiPath("/" + publishProperties.getServerId() + methodPaths[0].toString());
                apiDetail.setApiType(methodTypes[0].toString());
                apiDetail.setServerId(publishProperties.getServerId());
                apiDetail.setServerName(publishProperties.getServerName());
                // 放入集合
                apiDetailSet.add(apiDetail);
            }
        });
        return apiDetailSet;
    }

    private static boolean verifyClassPackageHasProperties(String classPackage,String... scanPackages){
        for (String scanPackage:scanPackages){
            if (Pattern.matches(buildRegexPackage(scanPackage), classPackage)){
                return true;
            }
        }
        return false;
    }

    private static ServerApis buildServerApis(ServerInterfacePublishProperties publishProperties){
        return new ServerApis()
                .setServerId(publishProperties.getServerId())
                .setServerName(publishProperties.getServerName());
    }

    private static String buildRegexPackage(String scanPackage) {
        return scanPackage.replace("**", "[\\w]*") + ".[\\w]*";
    }
}
