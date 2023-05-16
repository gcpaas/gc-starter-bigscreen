package com.gccloud.bigscreen.core.module.file.service;

import com.gccloud.bigscreen.core.module.file.dto.FileSearchDTO;
import com.gccloud.bigscreen.core.module.file.entity.BigScreenFileEntity;
import com.gccloud.bigscreen.core.service.ISuperService;
import com.gccloud.bigscreen.core.vo.PageVO;


/**
 * 文件管理
 *
 * @author liuchengbiao
 */
public interface IBigScreenFileService extends ISuperService<BigScreenFileEntity> {
    /**
     * 分页查询
     *
     * @param searchDTO
     * @return
     */
    PageVO<BigScreenFileEntity> getPage(FileSearchDTO searchDTO);

    /**
     * 更新下载次数
     *
     * @param addCount
     * @param fileId
     */
    void updateDownloadCount(Integer addCount, String fileId);
}
