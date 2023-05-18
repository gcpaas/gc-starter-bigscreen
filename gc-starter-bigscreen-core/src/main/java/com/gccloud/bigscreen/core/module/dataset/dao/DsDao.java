package com.gccloud.bigscreen.core.module.dataset.dao;

import com.gccloud.bigscreen.core.module.dataset.vo.DataSetInfoVo;
import com.gccloud.bigscreen.core.dao.BigScreenBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhang.zeJun
 * @date 2022-11-17-11:03
 */
@Mapper
public interface DsDao extends BigScreenBaseDao<DataSetInfoVo> {
}
