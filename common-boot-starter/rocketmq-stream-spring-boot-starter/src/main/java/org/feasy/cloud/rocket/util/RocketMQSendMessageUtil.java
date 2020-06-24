package org.feasy.cloud.rocket.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;

/**
 * <p>
 *  RocketMQ发送消息工具类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/6/2
 */
public class RocketMQSendMessageUtil {

    public static <T> void sendMessage(T value,MessageChannel messageChannel){
        Message<T> message = MessageBuilder.createMessage(value, buildDefaultMessageHeaders(null,null));
        messageChannel.send(message);
    }
    public static <T> void sendMessage(String keys,T value,MessageChannel messageChannel){
        Message<T> message = MessageBuilder.createMessage(value, buildDefaultMessageHeaders(keys,null));
        messageChannel.send(message);
    }
    public static <T> void sendMessage(String keys,String tags,T value,MessageChannel messageChannel){
        Message<T> message = MessageBuilder.createMessage(value, buildDefaultMessageHeaders(keys,tags));
        messageChannel.send(message);
    }

    /**
     * 构造消息头
     * @param keys  消息唯一标识 如果为空则自动生成UUID
     * @param tags  消息标签，消费时可以做过滤
     */
    private static MessageHeaders buildDefaultMessageHeaders(String keys,String tags){
        return new MessageHeaders(new HashMap<String, Object>(){{
            if(StringUtils.isNotBlank(keys)){
                put(MessageConst.PROPERTY_KEYS,keys);
            }
            if(StringUtils.isNotBlank(tags)){
                put(MessageConst.PROPERTY_TAGS,tags);
            }
        }});
    }




}
