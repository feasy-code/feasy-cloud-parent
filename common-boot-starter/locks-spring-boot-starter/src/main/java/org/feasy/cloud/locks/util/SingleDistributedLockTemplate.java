package org.feasy.cloud.locks.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
@Slf4j
public class SingleDistributedLockTemplate implements DistributedLockTemplate {
    private RedissonClient redisson;

    public SingleDistributedLockTemplate() {
    }

    public SingleDistributedLockTemplate(RedissonClient redisson) {
        this.redisson = redisson;
    }




    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback 回调
     * @param lockType 使用锁的类型 {@link LockType}
     */
    @Override
    public <T> T lock(DistributedLockCallback<T> callback, LockType lockType) {
        return lock(callback, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, lockType);
    }

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback  回调
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit  时间单位
     * @param lockType  使用锁的类型 {@link LockType}
     */
    @Override
    public <T> T lock(DistributedLockCallback<T> callback, long leaseTime, TimeUnit timeUnit, LockType lockType) {
        RLock lock = getLock(callback.getLockName(), lockType);
        try {
            lock.lock(leaseTime, timeUnit);
            return callback.process();
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试分布式锁，使用锁默认等待时间、超时时间。
     *
     * @param callback 回调
     * @param lockType 使用锁的类型 {@link LockType}
     */
    @Override
    public <T> T tryLock(DistributedLockCallback<T> callback, LockType lockType) {
        return tryLock(callback,DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, DEFAULT_TIME_UNIT, lockType);
    }

    /**
     * 尝试分布式锁，自定义等待时间、超时时间。
     *
     * @param callback  回调参数
     * @param waitTime  获取锁最长等待时间
     * @param leaseTime 锁超时时间。超时后自动释放锁。
     * @param timeUnit  时间单位
     * @param lockType  使用锁的类型 {@link LockType}
     */
    @Override
    public <T> T tryLock(DistributedLockCallback<T> callback, long waitTime, long leaseTime, TimeUnit timeUnit, LockType lockType) {
        RLock lock = getLock(callback.getLockName(), lockType);
        try {
            if (lock.tryLock(waitTime, leaseTime, timeUnit)) {
                return callback.process();
            }
        } catch (InterruptedException e) {

        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
        return null;
    }


    private RLock getLock(String key, LockType lockType) {
        switch (lockType) {
            case REENTRANT_LOCK:
                return redisson.getLock(key);

            case FAIR_LOCK:
                return redisson.getFairLock(key);

            case READ_LOCK:
                return redisson.getReadWriteLock(key).readLock();

            case WRITE_LOCK:
                return redisson.getReadWriteLock(key).writeLock();
            default:
                throw new RuntimeException("do not support lock type:" + lockType.name());
        }
    }
}
