package org.feasy.cloud.mysql.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * BaseService操作接口实现类
 * 封装统一项目数据库单表操作具体实现
 * @author yangxiaohui
 */
public class BaseServiceImpl <M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements BaseService<T> {
}
