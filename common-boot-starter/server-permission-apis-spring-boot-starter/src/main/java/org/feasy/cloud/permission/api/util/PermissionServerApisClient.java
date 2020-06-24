package org.feasy.cloud.permission.api.util;

import com.alibaba.fastjson.JSONObject;
import org.feasy.cloud.permission.api.entity.ServerApis;
import org.feasy.cloud.permission.api.global.ServerApisContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 请求获取信息
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Slf4j
@Component
public class PermissionServerApisClient {
    private Map<String,Integer> countGetServiceInstance=new ConcurrentHashMap<>();

    private RestTemplate restTemplate = new RestTemplate();

    private final ServerApisContainer serverApisContainer;

    public PermissionServerApisClient(ServerApisContainer serverApisContainer) {

        this.serverApisContainer = serverApisContainer;
    }

    public boolean serviceInstancesUpLine(ServiceInstance serviceInstance) {
        String serviceId=serviceInstance.getServiceId();
        try {
            String url;
            if (serviceInstance.isSecure()) {
                url = "https://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/serverInterface/serverApiDetails";
            } else {
                url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/serverInterface/serverApiDetails";
            }
            ServerApis serverApis = this.restTemplate.getForObject(url,ServerApis.class);
            serverApisContainer.addServerApis(serverApis);
            log.info("获取服务API:{}", JSONObject.toJSONString(serverApis));
        } catch (Exception e) {
            if (!countGetServiceInstance.containsKey(serviceId)){
                countGetServiceInstance.put(serviceId,0);
            }else if (countGetServiceInstance.get(serviceId)>9){
                return true;
            }else {
                log.error("{}重试获取：{}",serviceId,countGetServiceInstance.get(serviceId));
                countGetServiceInstance.put(serviceId,countGetServiceInstance.get(serviceId)+1);
                return false;
            }
        }
        return true;
    }

    public void serviceInstancesOffLine(String serviceId){
        countGetServiceInstance.remove(serviceId);
        serverApisContainer.removeServerApis(serviceId);
    }
}
