package com.gccloud.bigscreen.core.module.chart.components;

import com.gccloud.bigscreen.core.module.chart.bean.Chart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/24 14:36
 */
@Data
public class ScreenDigitalFlopChart extends Chart {

    @ApiModelProperty(notes = "个性化")
    private Customize customize = new Customize();

    @Data
    public static class Customize {

        @ApiModelProperty(notes = "内容")
        private String content;

        @ApiModelProperty(notes = "保留小数位数")
        private Integer toFixed;

        @ApiModelProperty(notes = "文本对齐方式")
        private String textAlign;

        @ApiModelProperty(notes = "行间距")
        private Integer rowGap;

        @ApiModelProperty(notes = "格式化")
        private String formatter;

        @ApiModelProperty(notes = "样式")
        private Style style = new Style();

    }

    @Data
    public static class Style {

        @ApiModelProperty(notes = "字体大小")
        private Integer fontSize;

        @ApiModelProperty(notes = "字体颜色")
        private String fill;
    }

}
