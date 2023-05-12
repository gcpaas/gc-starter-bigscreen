package com.gccloud.starter.lowcode.page.chart.components.bigscreen;

import com.gccloud.starter.lowcode.page.constant.PageDesignConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/13 16:44
 */
@Data
public class ScreenIframeChart {

    @ApiModelProperty(notes = "外链地址")
    private String url;

    @ApiModelProperty(notes = "类型")
    private String type = PageDesignConstant.BigScreen.Type.IFRAME;

}
