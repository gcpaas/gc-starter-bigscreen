package com.gccloud.bigscreen.core.module.file.dao;

import com.gccloud.bigscreen.core.dao.BigScreenBaseDao;
import com.gccloud.bigscreen.core.module.file.entity.BigScreenFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件
 */
@Mapper
public interface BigScreenFileDao extends BigScreenBaseDao<BigScreenFileEntity> {
    /**
     * 更新文件下载次数
     *
     * @param addCount
     */
    void updateDownloadCount(@Param("addCount") Integer addCount, @Param("id") String fileId);


    /**
     * 获取所有文件后缀(去重)
     * @return
     */
    List<String> getAllExtension();

}
