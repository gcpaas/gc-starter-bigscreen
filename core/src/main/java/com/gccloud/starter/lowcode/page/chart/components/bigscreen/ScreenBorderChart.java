package com.gccloud.starter.lowcode.page.chart.components.bigscreen;

import com.gccloud.starter.lowcode.page.chart.components.bean.Chart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 边框图表
 * @author hongyang
 * @version 1.0
 * @date 2023/3/16 10:29
 */
@Data
public class ScreenBorderChart extends Chart {

    @ApiModelProperty(notes = "个性化配置")
    private Customize customize = new Customize();

    @Data
    public static class Customize {

        @ApiModelProperty(notes = "边框主颜色")
        private String borderMainColor;

        @ApiModelProperty(notes = "边框次颜色")
        private String borderSecondaryColor;

        @ApiModelProperty(notes = "背景颜色")
        private String backgroundColor;

        @ApiModelProperty(notes = "边框颜色")
        private String borderColor;

        @ApiModelProperty(notes = "边框宽度")
        private Integer borderWidth;

        @ApiModelProperty(notes = "是否翻转")
        private Boolean reverse;

        @ApiModelProperty(notes = "单次动画时长")
        private Integer dur;

        @ApiModelProperty(notes = "颜色类型")
        private String colorType;

        @ApiModelProperty(notes = "渐变色0值")
        private String gradientColor0;

        @ApiModelProperty(notes = "渐变色1值")
        private String gradientColor1;

        @ApiModelProperty(notes = "渐变色色值改变方向")
        private String gradientDirection;

        @ApiModelProperty(notes = "透明度")
        private Integer opacity;



    }

}
