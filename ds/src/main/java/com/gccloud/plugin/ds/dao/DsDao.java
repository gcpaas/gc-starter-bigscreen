package com.gccloud.plugin.ds.dao;

import com.gccloud.plugin.ds.vo.DataSetInfoVo;
import com.gccloud.starter.common.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhang.zeJun
 * @date 2022-11-17-11:03
 */
@Mapper
public interface DsDao extends BaseDao<DataSetInfoVo> {
}
