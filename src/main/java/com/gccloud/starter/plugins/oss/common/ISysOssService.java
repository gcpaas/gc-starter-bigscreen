package com.gccloud.starter.plugins.oss.common;


import com.gccloud.starter.common.entity.SysFileEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件管理
 *
 * @author liuchengbiao
 */
public interface ISysOssService {
    /**
     * 上传文件
     *
     * @param file
     * @param entity
     * @param response
     * @param request
     * @return
     */
    SysFileEntity upload(MultipartFile file, SysFileEntity entity, HttpServletResponse response, HttpServletRequest request);

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @param request
     */
    void download(String fileId, HttpServletResponse response, HttpServletRequest request);
}
