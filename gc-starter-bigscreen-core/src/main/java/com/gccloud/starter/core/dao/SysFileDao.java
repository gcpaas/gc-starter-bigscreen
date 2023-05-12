package com.gccloud.starter.core.dao;

import com.gccloud.starter.common.entity.SysFileEntity;
import com.gccloud.starter.common.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文件
 */
@Mapper
public interface SysFileDao extends BaseDao<SysFileEntity> {
    /**
     * 更新文件下载次数
     *
     * @param addCount
     */
    void updateDownloadCount(@Param("addCount") Integer addCount, @Param("id") String fileId);
}
