package com.gccloud.starter.lowcode.page.bigscreen.service;

import com.gccloud.starter.lowcode.page.bigscreen.dto.BigScreenPageDTO;
import com.gccloud.starter.lowcode.page.service.IBasePageService;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/13 10:59
 */
public interface IBigScreenPageService  extends IBasePageService {

    /**
     * 从空白新增大屏页
     * @param bigScreenPageDTO
     * @return
     */
    String add(BigScreenPageDTO bigScreenPageDTO);


    /**
     * 从模板新增大屏页
     * @param bigScreenPageDTO
     * @return
     */
    String addByTemplate(BigScreenPageDTO bigScreenPageDTO);

    /**
     * 根据编码获取大屏页配置
     * @param bigScreenPageDTO
     * @return
     */
    BigScreenPageDTO getConfigByTemplate(BigScreenPageDTO bigScreenPageDTO);

    /**
     * 更新大屏页
     * @param bigScreenPageDTO
     */
    void update(BigScreenPageDTO bigScreenPageDTO);

    /**
     * 复制大屏页
     * @param sourceCode
     * @return
     */
    String copy(String sourceCode);

    /**
     * 根据编码删除
     * @param code
     */
    void deleteByCode(String code);

}
