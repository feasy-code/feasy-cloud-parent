package org.feasy.cloud.permission.api.global;

import org.feasy.cloud.permission.api.entity.ApiDetail;
import org.feasy.cloud.permission.api.entity.ServerApis;
import org.feasy.cloud.permission.api.event.ServerApisAddEvent;
import org.feasy.cloud.permission.api.event.ServerApisRemoveEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *  ServerApis 容器
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/27
 */
public class ServerApisContainer{
    /**
     * 所有接口信息
     */
    private List<ApiDetail> allApiDetails= Collections.synchronizedList(new LinkedList<ApiDetail>());
    /**
     * 所有服务接口信息
     */
    private Map<String, ServerApis> serverApisMap=new ConcurrentHashMap<>();

    private final ApplicationEventPublisher publisher;

    public ServerApisContainer(ApplicationEventPublisher publisher){
        this.publisher=publisher;
    }
    public ServerApisContainer(ApplicationEventPublisher publisher,ServerApis serverApis){
        this.publisher=publisher;
        this.addServerApis(serverApis);
    }
    public void addServerApis(ServerApis serverApis){
        this.serverApisMap.put(serverApis.getServerId(),serverApis);
        this.allApiDetails.addAll(serverApis.getApis());
        // 手动触发事件
        this.publisher.publishEvent(new ServerApisAddEvent(serverApis));
    }
    public List<ApiDetail> listAllApiDetail(){
        return this.allApiDetails;
    }
    public void removeServerApis(String serviceId){
        if (this.serverApisMap.containsKey(serviceId)) {
            // 手动触发事件
            this.publisher.publishEvent( new ServerApisRemoveEvent(this.serverApisMap.get(serviceId)));
            this.allApiDetails.removeAll(this.serverApisMap.get(serviceId).getApis());
            this.serverApisMap.remove(serviceId);
        }
    }

}
