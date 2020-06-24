package org.feasy.cloud.permission.api.event;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.feasy.cloud.permission.api.util.PermissionServerApisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  监听服务变化事件，处理获取Server列表和接口列表
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/26
 */
@Slf4j
public class WatchServerChangedLocator implements Ordered, ApplicationListener<WatchServerChangedEvent> {

    /**
     * 注册表服务信息
     */
    private Map<String,List<ServiceInstance>> serverInstanceMap=new ConcurrentHashMap<>();

    /**
     *  获取信息
     */
    private final NacosServiceDiscovery nacosServiceDiscovery;

    private final PermissionServerApisClient serverApisClient;

    private final String applicationName;

    public WatchServerChangedLocator(NacosServiceDiscovery nacosServiceDiscovery,PermissionServerApisClient serverApisClient,String applicationName) {
        this.nacosServiceDiscovery=nacosServiceDiscovery;
        this.serverApisClient=serverApisClient;
        this.applicationName=applicationName;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(WatchServerChangedEvent event) {
            // 监听到服务注册表有新的服务注册
        this.refreshServiceInstancesMap();
    }


    private void refreshServiceInstancesMap(){
        try {
            Set<String> validServiceIds = new HashSet<>();
            // 解析出处于健康状态的服务(忽略本机服务ID) 服务ID
            nacosServiceDiscovery.getServices().forEach(serviceId->{
                try {
                    if (!applicationName.equals(serviceId)&&!nacosServiceDiscovery.getInstances(serviceId).isEmpty()){
                        nacosServiceDiscovery.getInstances(serviceId).forEach(serviceInstance -> {
                            if(Boolean.parseBoolean(serviceInstance.getMetadata().get("nacos.healthy"))){
                                validServiceIds.add(serviceId);
                            }
                        });
                    }
                } catch (NacosException e) {
                    e.printStackTrace();
                }
            });
            // 判断服务列表里是否有健康服务下线了
            serverInstanceMap.forEach((key,value)->{
                if (!validServiceIds.contains(key)){
                    this.serverInstanceMap.remove(key);
                }
            });
            for (String serviceId : validServiceIds) {
                // 判断是否有健康服务上线
                List<ServiceInstance> serviceInstancesGroup=nacosServiceDiscovery.getInstances(serviceId);
                if (!serverInstanceMap.containsKey(serviceId)&&!serviceInstancesGroup.isEmpty()){
                    for (ServiceInstance serviceInstance:serviceInstancesGroup){
                        if(Boolean.parseBoolean(serviceInstance.getMetadata().get("nacos.healthy"))){
                            if (this.serviceInstancesUpLine(serviceInstance)){
                                this.serverInstanceMap.put(serviceId,serviceInstancesGroup);
                                break;
                            }
                        }
                    }


                }
            }
        } catch (NacosException e) {
            log.error("Nacos服务心跳异常！",e);
        }
    }

    /**
     * 服务下线
     */
    private void serviceInstancesOffLine(String serviceId){
        this.serverApisClient.serviceInstancesOffLine(serviceId);
    }

    /**
     * 服务上线
     */
    private boolean serviceInstancesUpLine(ServiceInstance serviceInstance){
        // 挑选一个健康的实例 获取服务信息
        return this.serverApisClient.serviceInstancesUpLine(serviceInstance);
    }
    @Override
    public int getOrder() {
        return 1000;
    }


}
