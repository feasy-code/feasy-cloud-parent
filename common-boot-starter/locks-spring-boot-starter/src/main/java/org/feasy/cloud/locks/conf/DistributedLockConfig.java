package org.feasy.cloud.locks.conf;

import org.feasy.cloud.locks.clilent.DistributedLockManager;
import org.feasy.cloud.locks.util.DistributedLockTemplate;
import org.feasy.cloud.locks.util.SingleDistributedLockTemplate;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 *      分布式锁Starter配置类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
@ConditionalOnBean(RedissonClient.class)
@AutoConfigureAfter(RedissonAutoConfiguration.class)
public class DistributedLockConfig {

    /**
     * 注解使用的加锁客户端
     */
    @Bean
    public DistributedLockTemplate distributedLockTemplate(RedissonClient redissonClient) {
        return new SingleDistributedLockTemplate(redissonClient);
    }

    /**
     * 手动加锁代码块
     */
    @Bean
    public DistributedLockManager distributedLockManager(){
        return new DistributedLockManager();
    }
}
