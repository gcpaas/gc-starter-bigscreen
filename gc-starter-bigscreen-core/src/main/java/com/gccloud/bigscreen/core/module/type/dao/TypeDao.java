package com.gccloud.bigscreen.core.module.type.dao;

import com.gccloud.bigscreen.core.dao.BigScreenBaseDao;
import com.gccloud.bigscreen.core.module.type.entity.TypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author qianxing
 * @Date 2022/06/07
 * @Version 1.0.0
 */
@Mapper
public interface TypeDao extends BigScreenBaseDao<TypeEntity> {

}