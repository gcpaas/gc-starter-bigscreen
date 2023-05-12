package com.gccloud.starter.core.service;

import com.gccloud.starter.common.vo.TreeVo;

import java.util.List;

/**
 * @author liuchengbiao
 * @date 2020-07-22 14:33
 */
public interface ITreeService {
    /**
     * 将平铺的数据转为符合树形的数据结构
     *
     * @param voList
     */
    void transToTree(List<? extends TreeVo> voList);

}
