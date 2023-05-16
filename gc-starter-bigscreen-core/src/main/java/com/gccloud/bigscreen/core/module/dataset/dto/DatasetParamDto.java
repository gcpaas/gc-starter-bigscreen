package com.gccloud.bigscreen.core.module.dataset.dto;

import lombok.Data;

/**
 * @Description:数据集参数
 * @Author yang.hw
 * @Date 2021/9/15 11:11
 */
@Data
public class DatasetParamDto {
    private String name;
    private String type;
    private String value;
    private Integer status;
}
