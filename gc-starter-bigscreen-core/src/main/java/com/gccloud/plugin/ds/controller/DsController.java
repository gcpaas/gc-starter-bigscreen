package com.gccloud.plugin.ds.controller;

import com.gccloud.plugin.ds.dto.ExecuteDto;
import com.gccloud.plugin.ds.service.DsService;
import com.gccloud.plugin.ds.vo.DataSetInfoVo;
import com.gccloud.starter.common.vo.R;
import com.gccloud.starter.core.controller.SuperController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 数据管理公共API
 *
 * @author pan.shun
 * @since 2022/9/7 09:57
 */
@RestController
@Api(tags = "数据管理公共接口")
@RequestMapping("/ds")
public class DsController extends SuperController {

    @Resource
    private DsService dsService;


    @ApiOperation("数据集详情")
    @GetMapping("/getDataSetDetails")
    public R<DataSetInfoVo> getDataSetDetails(String id) {
        DataSetInfoVo dataSetDetails = dsService.getDataSetDetails(id);
        return R.success(dataSetDetails);
    }

    @PostMapping("/getDataByDataSetId")
    public R<Object> getDataByDataSetId(@RequestBody ExecuteDto executeDto) {
        return R.success(dsService.execute(executeDto.getDataSetId(), executeDto.getParams()));
    }

}
