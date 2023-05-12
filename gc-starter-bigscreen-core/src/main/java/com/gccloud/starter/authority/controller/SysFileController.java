package com.gccloud.starter.authority.controller;

import com.gccloud.starter.common.entity.SysFileEntity;
import com.gccloud.starter.common.module.file.dto.FileSearchDTO;
import com.gccloud.starter.common.module.file.vo.SysFileVO;
import com.gccloud.starter.common.mybatis.page.PageVO;
import com.gccloud.starter.common.utils.BeanConvertUtils;
import com.gccloud.starter.common.vo.R;
import com.gccloud.starter.core.controller.SuperController;
import com.gccloud.starter.core.service.ISysFileService;
import com.gccloud.starter.plugins.oss.common.ISysOssService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件管理
 */
@Slf4j
@RestController
@RequestMapping("/sys/file")
@ConditionalOnProperty(prefix = "gc.starter.component", name = "SysFileController", havingValue = "SysFileController", matchIfMissing = true)
@Api(tags = "文件管理")
@ApiSort(value = 100)
public class SysFileController extends SuperController {

    @Resource
    private ISysOssService sysOssService;
    @Resource
    private ISysFileService fileService;

    @GetMapping(value = {"", "/"})
    @ApiOperation(value = "列表", position = 10, notes = "分页查询文件", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页条数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "searchKey", value = "查询条件", paramType = "query", dataType = "string")
    })
    public R<PageVO<SysFileVO>> getPage(@ApiParam(name = "查询", value = "传入查询的业务条件", required = true) FileSearchDTO searchDTO) {
        PageVO<SysFileEntity> page = fileService.getPage(searchDTO);
        PageVO<SysFileVO> pageVO = BeanConvertUtils.convertPage(page, SysFileVO.class);
        return R.success(pageVO);
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传", notes = "上传", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<SysFileEntity> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "module", required = false) String module, HttpServletResponse response, HttpServletRequest request) {
        SysFileEntity entity = new SysFileEntity();
        // 不同业务自己控制
        if (StringUtils.isBlank(module)) {
            module = "other";
        }
        entity.setModule(module);
        sysOssService.upload(file, entity, response, request);
        fileService.save(entity);
        return R.success(entity);
    }

    @PostMapping("/download")
    @ApiOperation(value = "下载", notes = "下载资源", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void download(@RequestBody SysFileEntity sysFileEntity, HttpServletResponse response, HttpServletRequest request) {
        sysOssService.download(sysFileEntity.getId(), response, request);
    }
}
