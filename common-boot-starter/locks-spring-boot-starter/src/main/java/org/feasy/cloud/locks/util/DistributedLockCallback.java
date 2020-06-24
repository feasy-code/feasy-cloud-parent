package org.feasy.cloud.locks.util;

/**
 * <p>
 *  分布式锁回调接口
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
public interface DistributedLockCallback<T> {
    /**
     * 调用者必须在此方法中实现需要加分布式锁的业务逻辑
     */
    T process();

    /**
     * 获取分布式锁名称
     */
    String getLockName();
}
