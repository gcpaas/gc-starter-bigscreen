package com.gccloud.bigscreen.core.module.dataset.controller;

import com.gccloud.bigscreen.core.module.dataset.dto.ExecuteDto;
import com.gccloud.bigscreen.core.module.dataset.service.DsService;
import com.gccloud.bigscreen.core.module.dataset.vo.DataSetInfoVo;
import com.gccloud.bigscreen.core.vo.R;
import com.gccloud.bigscreen.core.controller.SuperController;
import com.gccloud.bigscreen.core.permission.Permission;
import com.gccloud.bigscreen.core.permission.ScreenPermission;
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

    @ScreenPermission(permissions = {Permission.DataSet.VIEW})
    @ApiOperation("数据集详情")
    @GetMapping("/getDataSetDetails")
    public R<DataSetInfoVo> getDataSetDetails(String id) {
        DataSetInfoVo dataSetDetails = dsService.getDataSetDetails(id);
        return R.success(dataSetDetails);
    }

    @ScreenPermission(permissions = {Permission.DataSet.EXECUTE})
    @ApiOperation("数据集执行")
    @PostMapping("/getDataByDataSetId")
    public R<Object> getDataByDataSetId(@RequestBody ExecuteDto executeDto) {
        return R.success(dsService.execute(executeDto.getDataSetId(), executeDto.getParams()));
    }

}
