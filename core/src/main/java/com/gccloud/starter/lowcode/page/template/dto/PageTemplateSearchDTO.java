package com.gccloud.starter.lowcode.page.template.dto;

import com.gccloud.starter.common.dto.SearchDTO;
import com.gccloud.starter.lowcode.page.constant.PageDesignConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/20 16:38
 */
@Data
public class PageTemplateSearchDTO extends SearchDTO {

    /**
     * 参考：{@link PageDesignConstant.Type}
     */
    @ApiModelProperty(notes = "模板类型")
    private String type;

}
