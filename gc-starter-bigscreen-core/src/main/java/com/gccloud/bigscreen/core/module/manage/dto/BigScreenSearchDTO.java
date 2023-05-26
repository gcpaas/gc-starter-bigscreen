package com.gccloud.bigscreen.core.module.manage.dto;

import com.gccloud.bigscreen.core.dto.SearchDTO;
import lombok.Data;

/**
 * @author hongynag
 * @version 1.0
 */
@Data
public class BigScreenSearchDTO extends SearchDTO {

    /**
     * 所属分组
     */
    private String parentCode;

    /**
     * 类型
     */
    private String type;
}
