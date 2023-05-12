package com.gccloud.plugin.ds.service;

import com.gccloud.plugin.ds.dto.DatasetParamDto;
import com.gccloud.plugin.ds.vo.DataSetInfoVo;
import com.gccloud.starter.common.mybatis.page.PageVO;
import com.gccloud.starter.common.mybatis.service.ISuperService;

import java.util.List;
import java.util.Map;

/**
 * @author zhang.zeJun
 * @date 2022-11-17-10:27
 */
public interface DsService extends ISuperService<DataSetInfoVo> {

    /**
     * 根据数据集id查询数据集详情
     *
     * @param id
     * @return
     */
    DataSetInfoVo getDataSetDetails(String id);

    /**
     * 通过数据集ID 和参数查询数据
     *
     * @param dataSetId
     * @param params
     * @return
     */
    List<Map<String, Object>> execute(String dataSetId, List<DatasetParamDto> params);

    /**
     * 通过数据集ID 和参数 分页查询数据
     *
     * @param dataSetId
     * @param params
     * @param current
     * @param size
     * @return
     */
    PageVO execute(String dataSetId, List<DatasetParamDto> params, int current, int size);

    /**
     * 处理JSON数据集
     *
     * @param dataSetId
     * @return
     */
    Object executeJsonDataSet(String dataSetId);
}
