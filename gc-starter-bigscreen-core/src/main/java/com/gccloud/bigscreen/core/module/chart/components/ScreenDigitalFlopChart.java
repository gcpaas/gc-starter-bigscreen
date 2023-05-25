package com.gccloud.bigscreen.core.module.chart.components;

import com.gccloud.bigscreen.core.constant.PageDesignConstant;
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

    @ApiModelProperty(notes = "类型")
    private String type = PageDesignConstant.BigScreen.Type.DIGITAL_FLOP;

    @ApiModelProperty(notes = "个性化")
    private Customize customize = new Customize();

    @Data
    public static class Customize {

        @ApiModelProperty(notes = "字体颜色")
        private String color;

        @ApiModelProperty(notes = "背景颜色")
        private String bgColor;

        @ApiModelProperty(notes = "字体大小")
        private Integer fontSize;

        @ApiModelProperty(notes = "宽度")
        private Integer width;

        @ApiModelProperty(notes = "圆角")
        private Integer borderRadius;

        @ApiModelProperty(notes = "格式化")
        private Integer formatter;

        @ApiModelProperty(notes = "字体粗细")
        private String fontWeight;

        @ApiModelProperty(notes = "左侧插槽")
        private String slotLeft;

        @ApiModelProperty(notes = "右侧插槽")
        private String slotRight;

        @ApiModelProperty(notes = "右侧margin")
        private Integer marginRight;

        @ApiModelProperty(notes = "数字位数")
        private Integer numberDigits;

        @ApiModelProperty(notes = "占位符")
        private String placeHolder;



    }

}
