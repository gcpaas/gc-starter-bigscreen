package com.gccloud.starter.lowcode.page.dao;

import com.gccloud.starter.common.mybatis.dao.BaseDao;
import com.gccloud.starter.lowcode.page.entity.PageEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author qianxing
 * @Date 2022/06/07
 * @Version 1.0.0
 */
@Mapper
public interface PageDao extends BaseDao<PageEntity> {

}