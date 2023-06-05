package com.gccloud.bigscreen.core.module.biz.component.controller;

import com.gccloud.bigscreen.core.module.biz.component.BizComponentSearchDTO;
import com.gccloud.bigscreen.core.module.biz.component.entity.BizComponentEntity;
import com.gccloud.bigscreen.core.module.biz.component.service.IBizComponentService;
import com.gccloud.bigscreen.core.vo.PageVO;
import com.gccloud.bigscreen.core.vo.R;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/6/5 13:43
 */
@Slf4j
@RestController
@RequestMapping("/bigScreen/bizComponent")
@Api(tags = "业务组件")
@ApiSort(value = 110)
public class BizComponentController {


    @Resource
    private IBizComponentService bizComponentService;


    @GetMapping("/page")
    @ApiOperation(value = "分页", position = 10, notes = "分页查询业务组件", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "名称模糊查询", paramType = "query", dataType = "string")
    })
    public R<PageVO<BizComponentEntity>> getPage(@ApiParam(name = "查询", value = "传入查询的业务条件", required = true) BizComponentSearchDTO searchDTO) {
        PageVO<BizComponentEntity> page = bizComponentService.getPage(searchDTO);
        return R.success(page);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> add(@ApiParam(name = "新增", value = "传入新增的业务条件", required = true) @RequestBody BizComponentEntity entity) {
        bizComponentService.add(entity);
        return R.success(entity.getId());
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改", notes = "修改", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> update(@ApiParam(name = "修改", value = "传入修改的业务条件", required = true) @RequestBody BizComponentEntity entity) {
        bizComponentService.update(entity);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> delete(@ApiParam(name = "删除", value = "传入删除的业务条件", required = true) @PathVariable String id) {
        bizComponentService.delete(id);
        return R.success();
    }

    @GetMapping("/info/{code}")
    @ApiOperation(value = "根据编码获取组件", notes = "根据编码获取组件", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<BizComponentEntity> getInfoByCode(@ApiParam(name = "根据编码获取组件", value = "传入根据编码获取组件的业务条件", required = true) @PathVariable String code) {
        BizComponentEntity entity = bizComponentService.getInfoByCode(code);
        return R.success(entity);
    }

    @PostMapping("/name/repeat")
    @ApiOperation(value = "名称查重", notes = "名称查重", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> nameRepeat(@RequestBody BizComponentEntity entity) {
        return R.success(bizComponentService.checkName(entity.getId(), entity.getName()));
    }



}
