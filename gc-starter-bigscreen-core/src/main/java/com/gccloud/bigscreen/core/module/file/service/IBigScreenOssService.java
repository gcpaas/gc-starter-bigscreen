package com.gccloud.bigscreen.core.module.file.service;


import com.gccloud.bigscreen.core.module.file.entity.BigScreenFileEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件管理
 *
 * @author liuchengbiao
 */
public interface IBigScreenOssService {
    /**
     * 上传文件
     *
     * @param file
     * @param entity
     * @param response
     * @param request
     * @return
     */
    BigScreenFileEntity upload(MultipartFile file, BigScreenFileEntity entity, HttpServletResponse response, HttpServletRequest request);

    /**
     * 下载文件
     *
     * @param fileId
     * @param response
     * @param request
     */
    void download(String fileId, HttpServletResponse response, HttpServletRequest request);


    /**
     * 删除文件
     * @param fileId
     */
    void delete(String fileId);
}
