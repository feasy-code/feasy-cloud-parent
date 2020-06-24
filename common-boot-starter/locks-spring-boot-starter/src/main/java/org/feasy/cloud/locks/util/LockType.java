package org.feasy.cloud.locks.util;

/**
 * <p>
 *  锁类型枚举
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/5/20
 */
public enum LockType {
    /** 可重入锁*/
    REENTRANT_LOCK,
    /** 公平锁*/
    FAIR_LOCK,
    /** 读锁*/
    READ_LOCK,
    /** 写锁*/
    WRITE_LOCK;
}
