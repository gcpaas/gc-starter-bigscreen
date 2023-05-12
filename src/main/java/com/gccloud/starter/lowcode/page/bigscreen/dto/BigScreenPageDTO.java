package com.gccloud.starter.lowcode.page.bigscreen.dto;

import com.gccloud.starter.common.validator.group.Insert;
import com.gccloud.starter.common.validator.group.Update;
import com.gccloud.starter.lowcode.page.chart.components.bean.Chart;
import com.gccloud.starter.lowcode.page.constant.PageDesignConstant;
import com.gccloud.starter.lowcode.page.dto.BasePageDTO;
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

    @ApiModelProperty(notes = "所属应用编码")
    private String appCode;

    @NotBlank(message = "名称不能为空", groups = {Update.class, Insert.class})
    @ApiModelProperty(notes = "大屏名称")
    private String name;

    @NotBlank(message = "编码不能为空", groups = {Update.class})
    @ApiModelProperty(notes = "大屏唯一标识符")
    private String code;

    @ApiModelProperty(notes = "大屏页图标")
    private String icon;

    @ApiModelProperty(notes = "大屏页颜色")
    private String iconColor;

    @ApiModelProperty(notes = "排序")
    private Integer orderNum;

    @ApiModelProperty(notes = "备注")
    private String remark;

    @ApiModelProperty(notes = "大屏整体样式")
    private String style;

    @ApiModelProperty(notes = "父节点编码")
    private String parentCode = "0";

    @ApiModelProperty(notes = "图表数据")
    private List<Chart> chartList;

    @ApiModelProperty(notes = "出码目录")
    private String codePath;

    @ApiModelProperty(notes = "页面模板id")
    private String pageTemplateId;

    public String getCodePath() {
        if (StringUtils.isBlank(codePath)) {
            return code;
        }
        return codePath;
    }

    /**
     * 参考：{@link PageDesignConstant.Type}
     */
    @ApiModelProperty(notes = "类型，只能是bigScreen")
    private String type = PageDesignConstant.Type.BIG_SCREEN;

    @ApiModelProperty(notes = "页面配置")
    private PageConfig pageConfig;

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
