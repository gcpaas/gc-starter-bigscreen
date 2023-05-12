package com.gccloud.starter.lowcode.page.category.controller;

import com.gccloud.starter.common.validator.ValidatorUtils;
import com.gccloud.starter.common.validator.group.Insert;
import com.gccloud.starter.common.validator.group.Update;
import com.gccloud.starter.common.vo.R;
import com.gccloud.starter.core.controller.SuperController;
import com.gccloud.starter.lowcode.page.category.dto.CategoryDTO;
import com.gccloud.starter.lowcode.page.category.dto.CategorySearchDTO;
import com.gccloud.starter.lowcode.page.category.service.ICategoryService;
import com.gccloud.starter.lowcode.page.category.vo.CategoryVO;
import com.gccloud.starter.lowcode.page.entity.PageEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 页面目录
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Slf4j
@RestController
@RequestMapping("/category")
@Api(tags = "页面目录")
public class CategoryController extends SuperController {

    @Resource
    private ICategoryService categoryService;


    @PostMapping("/tree")
    @ApiOperation(value = "目录树", position = 20, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<CategoryVO>> getTree(@RequestBody CategorySearchDTO searchDTO) {
        List<CategoryVO> treeList = categoryService.getTree(searchDTO);
        return success(treeList);
    }

    @PostMapping("/list")
    @ApiOperation(value = "列表", position = 20, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<CategoryVO>> getList(@RequestBody CategorySearchDTO searchDTO) {
        List<CategoryVO> list = categoryService.getList(searchDTO);
        return success(list);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增目录", position = 30, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> add(@RequestBody CategoryDTO categoryDTO) {
        ValidatorUtils.validateEntity(categoryDTO, Insert.class);
        String code = categoryService.add(categoryDTO);
        return success(code);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新目录", position = 40, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> update(@RequestBody CategoryDTO categoryDTO) {
        ValidatorUtils.validateEntity(categoryDTO, Update.class);
        PageEntity pageEntity = categoryService.getEntityByField(PageEntity::getCode,categoryDTO.getCode());
        if(pageEntity == null){
            return success();
        }
        String code = categoryService.update(categoryDTO);
        return success(code);
    }

    @PostMapping("/delete/{code}")
    @ApiOperation(value = "删除目录", position = 60, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> deleteById(@PathVariable("code") String code) {
        PageEntity pageEntity = categoryService.getEntityByField(PageEntity::getCode,code);
        if(pageEntity == null){
            return success();
        }
        categoryService.deleteByCode(code);
        return success();
    }

}
