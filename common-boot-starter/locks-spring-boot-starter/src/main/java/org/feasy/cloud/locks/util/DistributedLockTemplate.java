package org.feasy.cloud.locks.util;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 分布式锁操作模板
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
public interface DistributedLockTemplate {
    long DEFAULT_WAIT_TIME = 30000;
    long DEFAULT_TIMEOUT = 5000;
    TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MILLISECONDS;

    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback  回调
     * @param lockType 使用锁的类型 {@link LockType}
     */
    <T> T lock(DistributedLockCallback<T> callback, LockType lockType);

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback  回调
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit  时间单位
     * @param lockType  使用锁的类型 {@link LockType}
     */
    <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, LockType lockType);

    /**
     * 尝试分布式锁，使用锁默认等待时间、超时时间。
     *
     * @param callback 回调
     * @param lockType  使用锁的类型 {@link LockType}
     */
    <T> T tryLock(DistributedLockCallback<T> callback, LockType lockType);

    /**
     * 尝试分布式锁，自定义等待时间、超时时间。
     *
     * @param callback  回调参数
     * @param waitTime  获取锁最长等待时间
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit  时间单位
     * @param lockType  使用锁的类型 {@link LockType}
     */
    <T> T tryLock(DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, LockType lockType);

}
