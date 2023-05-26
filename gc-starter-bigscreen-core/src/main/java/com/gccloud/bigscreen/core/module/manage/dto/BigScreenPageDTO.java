package com.gccloud.bigscreen.core.module.manage.dto;

import com.gccloud.bigscreen.core.module.chart.bean.Chart;
import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.validator.group.Insert;
import com.gccloud.bigscreen.core.validator.group.Update;
import com.gccloud.bigscreen.core.module.basic.dto.BasePageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 大屏页面
 * @author hongyang
 * @version 1.0
 * @date 2023/3/13 10:49
 */
@Data
public class BigScreenPageDTO extends BasePageDTO {

    @NotBlank(message = "id不能为空", groups = Update.class)
    @ApiModelProperty(notes = "主键id")
    private String id;

    @NotBlank(message = "名称不能为空", groups = {Update.class, Insert.class})
    @ApiModelProperty(notes = "大屏名称")
    private String name;

    @NotBlank(message = "编码不能为空", groups = {Update.class})
    @ApiModelProperty(notes = "大屏唯一标识符")
    private String code;

    @ApiModelProperty(notes = "大屏页图标")
    private String icon;

    @ApiModelProperty(notes = "大屏首页封面")
    private String coverPicture;

    @ApiModelProperty(notes = "大屏页颜色")
    private String iconColor;

    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    @ApiModelProperty(notes = "备注")
    private String remark;

    @ApiModelProperty(notes = "大屏整体样式")
    private String style;

    @ApiModelProperty(notes = "父节点编码")
    private String parentCode;

    @ApiModelProperty(notes = "图表数据")
    private List<Chart> chartList;

    @ApiModelProperty(notes = "页面模板id")
    private String pageTemplateId;

    /**
     * 参考：{@link PageDesignConstant.Type}
     */
    @NotBlank(message = "类型不能为空", groups = {Update.class, Insert.class})
    @ApiModelProperty(notes = "类型")
    private String type;

    @ApiModelProperty(notes = "页面配置")
    private PageConfig pageConfig;

    @ApiModelProperty(notes = "所属应用编码")
    private String appCode;

    @Data
    public static class CacheDataSet {
        @ApiModelProperty(notes = "数据集名称")
        private String name;

        @ApiModelProperty(notes = "数据集id")
        private String dataSetId;
    }

    @Data
    public static class PageConfig {

        @ApiModelProperty(notes = "宽度")
        private Integer w;

        @ApiModelProperty(notes = "高度")
        private Integer h;

        @ApiModelProperty(notes = "背景色")
        private String bgColor;

        @ApiModelProperty(notes = "背景图")
        private String bg;

        @ApiModelProperty(notes = "自定义主题")
        private String customTheme;

        @ApiModelProperty(notes = "透明度")
        private Float opacity;

        @ApiModelProperty(notes = "缓存数据集")
        private List<CacheDataSet> cacheDataSets;

        @ApiModelProperty(notes = "自适应类型")
        private String fitMode;
    }

}
