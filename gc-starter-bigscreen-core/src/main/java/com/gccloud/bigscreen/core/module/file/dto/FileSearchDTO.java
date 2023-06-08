package com.gccloud.bigscreen.core.module.file.dto;

import com.gccloud.common.dto.SearchDTO;
import lombok.Data;

@Data
public class FileSearchDTO extends SearchDTO {

    /**
     * 所属模块
     */
    private String module;

    /**
     * 文件后缀
     */
    private String extension;


}
