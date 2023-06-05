package com.gccloud.bigscreen.core.module.biz.component;

import com.gccloud.bigscreen.core.dto.SearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/6/5 11:41
 */
@Data
public class BizComponentSearchDTO extends SearchDTO {

    @ApiModelProperty(value = "所属分组")
    private String group;

    @ApiModelProperty(value = "名称")
    private String name;
}
