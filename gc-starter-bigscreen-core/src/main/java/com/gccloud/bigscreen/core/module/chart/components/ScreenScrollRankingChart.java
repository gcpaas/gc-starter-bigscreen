package com.gccloud.bigscreen.core.module.chart.components;

import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.module.chart.bean.Chart;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/22 16:10
 */
@Data
public class ScreenScrollRankingChart extends Chart {

    @ApiModelProperty(notes = "类型")
    private String type = PageDesignConstant.BigScreen.Type.SCREEN_SCROLL_RANKING;

    @ApiModelProperty(notes = "个性化")
    private Customize customize = new Customize();

    @Data
    public static class Customize {

        @ApiModelProperty(notes = "滚动条数")
        private Integer rowNum;

        @ApiModelProperty(notes = "滚动间隔时间")
        private Integer waitTime;

        @ApiModelProperty(notes = "是否轮播")
        private String carousel;

        @ApiModelProperty(notes = "单位")
        private String unit;

        @ApiModelProperty(notes = "是否自动排序")
        private Boolean sort;

    }

}
