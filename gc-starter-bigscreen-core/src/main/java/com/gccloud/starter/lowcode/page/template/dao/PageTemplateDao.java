package com.gccloud.starter.lowcode.page.template.dao;

import com.gccloud.starter.common.mybatis.dao.BaseDao;
import com.gccloud.starter.lowcode.page.template.entity.PageTemplateEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/20 16:41
 */
@Mapper
public interface PageTemplateDao extends BaseDao<PageTemplateEntity> {
}
