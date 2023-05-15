package com.gccloud.plugin.ds.controller;

import com.gccloud.plugin.ds.entity.CategoryTree;
import com.gccloud.plugin.ds.service.CategoryTreeService;
import com.gccloud.starter.common.vo.R;
import com.gccloud.starter.core.controller.SuperController;
import com.gccloud.starter.lowcode.permission.Permission;
import com.gccloud.starter.lowcode.permission.ScreenPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author pan.shun
 * @since 2021/9/7 11:05
 */
@Api(tags = "种类树")
@RestController
@RequestMapping("/category")
public class CategoryTreeController extends SuperController {

    @Resource
    private CategoryTreeService categoryTreeService;

    @ScreenPermission(permissions = {Permission.DataSet.CATEGORY})
    @ApiOperation("依据类型查询对应的种类树")
    @GetMapping("/queryTreeList")
    public R<List<CategoryTree>> queryTreeList(String tableName, String moduleCode, Integer ifFilter) {
        return R.success(categoryTreeService.queryCategoryTree(tableName, moduleCode, ifFilter));
    }

    @ScreenPermission(permissions = {Permission.DataSet.CATEGORY})
    @ApiOperation("种类树新增或修改")
    @PostMapping("/addOrUpdateTree")
    public void addOrUpdateTree(@RequestBody CategoryTree categoryTree) {
        categoryTreeService.addOrUpdateTree(categoryTree);
    }

    @ScreenPermission(permissions = {Permission.DataSet.CATEGORY})
    @ApiOperation("根据ID删除种类树")
    @GetMapping("/remove/{id}")
    public void removeNode(@PathVariable String id) {
        categoryTreeService.removeNodeById(id);
    }


}
