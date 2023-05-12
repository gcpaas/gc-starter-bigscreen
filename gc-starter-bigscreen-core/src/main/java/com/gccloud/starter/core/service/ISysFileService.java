package com.gccloud.starter.core.service;

import com.gccloud.starter.common.entity.SysFileEntity;
import com.gccloud.starter.common.module.file.dto.FileSearchDTO;
import com.gccloud.starter.common.mybatis.page.PageVO;
import com.gccloud.starter.common.mybatis.service.ISuperService;


/**
 * 文件管理
 *
 * @author liuchengbiao
 */
public interface ISysFileService extends ISuperService<SysFileEntity> {
    /**
     * 分页查询
     *
     * @param searchDTO
     * @return
     */
    PageVO<SysFileEntity> getPage(FileSearchDTO searchDTO);

    /**
     * 更新下载次数
     *
     * @param addCount
     * @param fileId
     */
    void updateDownloadCount(Integer addCount, String fileId);
}
