package com.gccloud.bigscreen.core.module.category.dto;

import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.validator.group.Insert;
import com.gccloud.bigscreen.core.validator.group.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 目录查询
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Data
public class CategoryDTO {

    @NotBlank(message = "id不能为空", groups = Update.class)
    @ApiModelProperty(notes = "主键id")
    private String id;

    @NotBlank(message = "名称不能为空", groups = {Update.class, Insert.class})
    @ApiModelProperty(notes = "名称")
    private String name;

    @NotBlank(message = "编码不能为空", groups = {Update.class})
    @ApiModelProperty(notes = "编码")
    private String code;

    @ApiModelProperty(notes = "应用编码")
    private String appCode;

    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    /**
     * 参考：{@link PageDesignConstant}
     */
    @ApiModelProperty(notes = "目录类型")
    private String type = PageDesignConstant.Type.CATALOG;

    @ApiModelProperty(notes = "父编码")
    private String parentCode = "0";

}
