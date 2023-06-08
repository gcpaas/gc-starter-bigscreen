package com.gccloud.bigscreen.core.module.chart.service;

import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.module.chart.bean.Chart;
import com.gccloud.bigscreen.core.module.chart.bean.Filter;
import com.gccloud.bigscreen.core.module.chart.components.datasource.BaseChartDataSource;
import com.gccloud.bigscreen.core.module.chart.components.datasource.DataSetDataSource;
import com.gccloud.bigscreen.core.module.chart.dto.ChartDataSearchDTO;
import com.gccloud.bigscreen.core.module.chart.vo.ChartDataVO;
import com.gccloud.common.exception.GlobalException;
import com.gccloud.common.utils.JSON;
import com.gccloud.common.vo.PageVO;
import com.gccloud.dataset.constant.DatasetConstant;
import com.gccloud.dataset.dto.DatasetParamDTO;
import com.gccloud.dataset.entity.DatasetEntity;
import com.gccloud.dataset.entity.config.JsonDataSetConfig;
import com.gccloud.dataset.params.ParamsClient;
import com.gccloud.dataset.service.IBaseDataSetService;
import com.gccloud.dataset.service.factory.DataSetServiceFactory;
import com.gccloud.dataset.vo.DatasetInfoVO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Data
@Slf4j
@Service
public class BaseChartDataService {

    @Resource
    private DataSetServiceFactory dataSetServiceFactory;

    @Resource
    private ParamsClient paramsClient;

    public ChartDataVO dataQuery(Chart chart, ChartDataSearchDTO searchDTO) {
        BaseChartDataSource dataSource = chart.getDataSource();
        if (dataSource == null) {
            return null;
        }
        if (!dataSource.getClass().equals(DataSetDataSource.class)) {
            return null;
        }
        DataSetDataSource dataSetDataSource = (DataSetDataSource) dataSource;
        IBaseDataSetService dataSetService = dataSetServiceFactory.buildById(dataSetDataSource.getBusinessKey());
        DatasetEntity datasetEntity = dataSetService.getById(dataSetDataSource.getBusinessKey());
        if (datasetEntity == null) {
            return null;
        }
        if (DatasetConstant.DataSetType.JSON.equals(datasetEntity.getDatasetType())) {
            return jsonDataQuery(datasetEntity, dataSetDataSource, dataSetService);
        }
        return dataSetDataQuery(dataSetDataSource, chart,  searchDTO, dataSetService);
    }



    /**
     * json类型的数据集数据处理
     * @param dataSet
     * @param dataSetDataSource
     * @return
     */
    private ChartDataVO jsonDataQuery(DatasetEntity dataSet, DataSetDataSource dataSetDataSource, IBaseDataSetService dataSetService) {
        ChartDataVO dataDTO = new ChartDataVO();
        JsonDataSetConfig config = (JsonDataSetConfig) dataSet.getConfig();
        Object jsonContent = dataSetService.getData(null, null, config.getJson(), null);
        List<Map<String, Object>> data = Lists.newArrayList();
        if (jsonContent instanceof JSONArray) {
            jsonContent = ((JSONArray) jsonContent).toList();
        }
        if (jsonContent instanceof ArrayList) {
            ArrayList list = (ArrayList) jsonContent;
            for (Object o : list) {
                if (o instanceof HashMap) {
                    data.add((HashMap<String, Object>) o);
                }
            }
        }
        if (jsonContent instanceof HashMap) {
            HashMap map = (HashMap) jsonContent;
            data.add(map);
        }
        if (jsonContent instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) jsonContent;
            data.add(jsonObject.toMap());
        }
        HashMap<String, ChartDataVO.ColumnData> columnData = Maps.newHashMap();
        Map<String, Object> fieldDesc = config.getFieldDesc();
        fieldDesc.forEach((k, v) -> {
            ChartDataVO.ColumnData column = new ChartDataVO.ColumnData();
            column.setOriginalColumn(k);
            column.setAlias(k);
            column.setRemark(v.toString());
            columnData.put(k, column);
        });
        dataDTO.setData(data);
        dataDTO.setSuccess(true);
        dataDTO.setColumnData(columnData);
        return dataDTO;
    }


    /**
     * 根据数据集数据源查询数据
     * @param dataSource
     * @return
     */
    private ChartDataVO dataSetDataQuery(DataSetDataSource dataSource, Chart chart, ChartDataSearchDTO searchDTO, IBaseDataSetService dataSetService) {
        ChartDataVO dataDTO = new ChartDataVO();
        List<DatasetParamDTO> params = Lists.newArrayList();
        if (StringUtils.isBlank(dataSource.getBusinessKey())) {
            throw new GlobalException("图表未配置数据集");
        }
        DatasetInfoVO dataSetInfoVo = dataSetService.getInfoById(dataSource.getBusinessKey());
        HashMap<String, ChartDataVO.ColumnData> columnData = Maps.newHashMap();
        List<Map<String, Object>> fieldJson = dataSetInfoVo.getFieldJson();
        fieldJson.forEach(field -> {
            ChartDataVO.ColumnData column = new ChartDataVO.ColumnData();
            column.setOriginalColumn(field.get("name").toString());
            column.setAlias(field.get("name").toString());
            column.setRemark(field.get("comment").toString());
            String sourceTable = field.get("sourceTable") == null ? "" : field.get("sourceTable").toString();
            column.setTableName(sourceTable);
            String type = field.get("type") == null ? "" : field.get("type").toString();
            column.setType(type);
            columnData.put(field.get("name").toString(), column);
        });
        if (chart.getType().equals(PageDesignConstant.BigScreen.Type.TABLES)) {
            // 表格的话，要按照dimensionFieldList对columnData进行排序
            List<String> dimensionFieldList = dataSource.getDimensionFieldList();
            LinkedHashMap<String, ChartDataVO.ColumnData> newColumnData = Maps.newLinkedHashMap();
            dimensionFieldList.forEach(dimensionField -> newColumnData.put(dimensionField, columnData.get(dimensionField)));
            // 剩下的字段按照原来的顺序放到后面
            columnData.forEach((key, value) -> {
                if (!newColumnData.containsKey(key)) {
                    newColumnData.put(key, value);
                }
            });
        }
        if (dataSource.getParams() != null && dataSource.getParams().size() > 0) {
            List<DatasetParamDTO> setParams = dataSetInfoVo.getParams();
            for (DatasetParamDTO param : setParams) {
                if (!dataSource.getParams().containsKey(param.getName())) {
                    continue;
                }
                String value = dataSource.getParams().get(param.getName()).toString();
                // 如果传入了过滤条件，优先使用过滤条件
                if (searchDTO.getFilterList() != null && searchDTO.getFilterList().size() > 0) {
                    for (Filter filter : searchDTO.getFilterList()) {
                        if (filter.getColumn() == null) {
                            continue;
                        }
                        if (filter.getColumn().equals(param.getName())) {
                            if (filter.getValue() == null || filter.getValue().size() == 0) {
                                continue;
                            }
                            value = filter.getValue().get(0);
                            break;
                        }
                    }
                }
                param.setValue(value);
                param.setStatus(1);
                params.add(param);
            }
        } else {
            // 组件配置的数据集参数为空，则使用数据集默认的参数
            List<DatasetParamDTO> setParams = dataSetInfoVo.getParams();
            if (setParams == null) {
                setParams = Lists.newArrayList();
            }
            params = setParams;
        }
        dataDTO.setColumnData(columnData);
        Object data;
        log.info("查询数据集数据，SQL：{}", dataDTO.getSql().replace("\n", " "));
        log.info("查询数据集数据，参数：{}", JSON.toJSONString(params));
        if (dataSource.getServerPagination() != null && dataSource.getServerPagination() && searchDTO.getSize() != null && searchDTO.getCurrent() != null) {
            PageVO<Object> pageResult = dataSetService.getPageData(null, null, dataSource.getBusinessKey(), params, searchDTO.getCurrent(), searchDTO.getSize());
            data = pageResult.getList();
            dataDTO.setTotalCount((int)pageResult.getTotalCount());
            dataDTO.setTotalPage((int)pageResult.getTotalPage());
        } else {
            data = dataSetService.getData(null, null, dataSource.getBusinessKey(), params);
        }
        dataDTO.setData(data);
        dataDTO.setSuccess(true);
        return dataDTO;
    }


    /**
     * 获取聚合函数汉化
     * @param aggregate
     * @return
     */
    public String getAggregateName(String aggregate) {
        switch (aggregate) {
            case PageDesignConstant.BigScreen.Aggregate.COUNT:
                return "[统计]";
            case PageDesignConstant.BigScreen.Aggregate.SUM:
                return "[求和]";
            case PageDesignConstant.BigScreen.Aggregate.AVG:
                return "[平均值]";
            case PageDesignConstant.BigScreen.Aggregate.MAX:
                return "[最大值]";
            case PageDesignConstant.BigScreen.Aggregate.MIN:
                return "[最小值]";
            case PageDesignConstant.BigScreen.Aggregate.COUNT_DISTINCT:
                return "[去重统计]";
            default:
                return "[" + aggregate + "]";
        }
    }
}
