package com.gccloud.bigscreen.core.module.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 目录查询
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Data
public class CategorySearchDTO {

    @ApiModelProperty(notes = "搜索关键词")
    private String searchKey;

    @ApiModelProperty(notes = "应用编码")
    private String appCode;

    @ApiModelProperty(notes = "排除的目录")
    private String excludeCategory;

    @ApiModelProperty(notes = "类型")
    private List<String> typeList;

    @ApiModelProperty(notes = "排序 按照目录>大屏>表格>表单进行排序，false时按照新增时间降序排序")
    private Boolean sort = Boolean.FALSE;
}
