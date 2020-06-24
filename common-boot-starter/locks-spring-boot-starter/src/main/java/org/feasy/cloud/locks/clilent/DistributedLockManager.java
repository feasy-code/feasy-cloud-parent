package org.feasy.cloud.locks.clilent;

import org.feasy.cloud.locks.DistributedLock;

/**
 * <p>
 *  分布式锁管理器
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
public class DistributedLockManager {
    @DistributedLock(lockName = "lock", lockNamePost = ".lock")
    public void doSomething(Action action) {
        action.action();
    }
}
