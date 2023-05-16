package com.gccloud.bigscreen.core.module.category.vo;

import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Data
public class CategoryVO {

    @ApiModelProperty(notes = "主键id")
    private String id;

    @ApiModelProperty(notes = "名称")
    private String name;

    @ApiModelProperty(notes = "编码")
    private String code;

    /**
     * 参考：{@link PageDesignConstant}
     */
    @ApiModelProperty(notes = "页面类型")
    private String type;

    @ApiModelProperty(notes = "排序")
    private Integer sort;

    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    @ApiModelProperty(notes = "父编码")
    private String parentCode;

    @ApiModelProperty(notes = "出码目录")
    private String codePath;

    @ApiModelProperty(notes = "子节点")
    private List<CategoryVO> children;
}
