package com.gccloud.bigscreen.core.module.biz.component.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.entity.SuperEntity;
import com.gccloud.bigscreen.core.module.basic.dto.BasePageDTO;
import com.gccloud.bigscreen.core.module.basic.entity.type.BasePageDTOTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 页面基本信息表
 *
 * @Author qianxing
 * @Date 2022/06/07
 * @Version 1.0.0
 */
@Data
@TableName(value = "big_screen_biz_component")
@ApiModel
public class BizComponentEntity extends SuperEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "业务组件中文名称")
    private String name;

    @ApiModelProperty(notes = "业务组件编码，页面唯一标识符")
    private String code;

    @ApiModelProperty(notes = "业务组件所属分组")
    private String group;

    @ApiModelProperty(notes = "组件封面")
    private String coverPicture;

    @ApiModelProperty(notes = "vue组件内容")
    private String vueContent;

    @ApiModelProperty(notes = "组件配置内容")
    private String settingContent;

    @ApiModelProperty(notes = "备注")
    private String remark;

    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    @ApiModelProperty(notes = "模块编码")
    private String moduleCode;

}
