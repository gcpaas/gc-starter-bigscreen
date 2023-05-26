package com.gccloud.bigscreen.core.module.manage.controller;

import com.gccloud.bigscreen.core.config.BigScreenConfig;
import com.gccloud.bigscreen.core.constant.BigScreenConst;
import com.gccloud.bigscreen.core.dto.SearchDTO;
import com.gccloud.bigscreen.core.exception.GlobalException;
import com.gccloud.bigscreen.core.module.manage.dto.BigScreenPageDTO;
import com.gccloud.bigscreen.core.module.manage.dto.BigScreenSearchDTO;
import com.gccloud.bigscreen.core.module.manage.vo.StaticFileVO;
import com.gccloud.bigscreen.core.vo.MixinsResp;
import com.gccloud.bigscreen.core.module.basic.entity.PageEntity;
import com.gccloud.bigscreen.core.permission.Permission;
import com.gccloud.bigscreen.core.permission.ScreenPermission;
import com.gccloud.bigscreen.core.utils.BeanConvertUtils;
import com.gccloud.bigscreen.core.validator.ValidatorUtils;
import com.gccloud.bigscreen.core.validator.group.Insert;
import com.gccloud.bigscreen.core.validator.group.Update;
import com.gccloud.bigscreen.core.vo.PageVO;
import com.gccloud.bigscreen.core.vo.R;
import com.gccloud.bigscreen.core.module.manage.service.IBigScreenPageService;
import com.gccloud.bigscreen.core.utils.Webjars;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/13 10:55
 */
@Slf4j
@RestController
@RequestMapping("/bigScreen/design")
@Api(tags = "大屏页以及大屏组件设计")
public class BigScreenPageController {

    @Resource
    private IBigScreenPageService bigScreenPageService;
    @Resource
    private BigScreenConfig bigScreenConfig;

    @ScreenPermission(permissions = {Permission.Screen.VIEW})
    @GetMapping("/info/code/{code}")
    @ApiOperation(value = "大屏页/组件详情", position = 10, produces = MediaType.APPLICATION_JSON_VALUE)
    public MixinsResp<BigScreenPageDTO> info(@PathVariable("code") String code) {
        PageEntity bigScreen = bigScreenPageService.getByCode(code);
        BigScreenPageDTO bigScreenPageDTO = (BigScreenPageDTO) bigScreen.getConfig();
        BeanConvertUtils.convert(bigScreen, bigScreenPageDTO);
        MixinsResp<BigScreenPageDTO> resp = new MixinsResp<BigScreenPageDTO>().setData(bigScreenPageDTO);
        resp.setCode(BigScreenConst.Response.Code.SUCCESS);
        return resp;
    }

    @ScreenPermission(permissions = {Permission.Screen.VIEW})
    @GetMapping("/page")
    @ApiOperation(value = "大屏/组件分页列表", position = 10, produces = MediaType.APPLICATION_JSON_VALUE)
    public MixinsResp<PageVO<PageEntity>> page(BigScreenSearchDTO searchDTO) {
        PageVO<PageEntity> page = bigScreenPageService.getByCategory(searchDTO);
        MixinsResp<PageVO<PageEntity>> resp = new MixinsResp<PageVO<PageEntity>>().setData(page);
        resp.setCode(BigScreenConst.Response.Code.SUCCESS);
        return resp;
    }


    @ScreenPermission(permissions = {Permission.Screen.EDIT})
    @PostMapping("/add")
    @ApiOperation(value = "从空白新增大屏/组件", position = 20, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> add(@RequestBody BigScreenPageDTO bigScreenPageDTO) {
        ValidatorUtils.validateEntity(bigScreenPageDTO, Insert.class);
        bigScreenPageService.add(bigScreenPageDTO);
        if (StringUtils.isBlank(bigScreenPageDTO.getParentCode())) {
            bigScreenPageDTO.setParentCode("0");
        }
        return R.success(bigScreenPageDTO.getCode());
    }

    @ScreenPermission(permissions = {Permission.Screen.EDIT})
    @PostMapping("/update")
    @ApiOperation(value = "修改大屏/组件", position = 30, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> update(@RequestBody BigScreenPageDTO bigScreenPageDTO) {
        ValidatorUtils.validateEntity(bigScreenPageDTO, Update.class);
        if (StringUtils.isBlank(bigScreenPageDTO.getParentCode())) {
            bigScreenPageDTO.setParentCode("0");
        }
        bigScreenPageService.update(bigScreenPageDTO);
        return R.success(bigScreenPageDTO.getCode());
    }

    @ScreenPermission(permissions = {Permission.Screen.DELETE})
    @PostMapping("/delete/{code}")
    @ApiOperation(value = "删除大屏/组件", position = 40, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> delete(@PathVariable String code) {
        PageEntity bigScreenPage = bigScreenPageService.getByCode(code);
        if (bigScreenPage == null) {
            return R.success();
        }
        bigScreenPageService.deleteByCode(code);
        return R.success();
    }

    @ScreenPermission(permissions = {Permission.Screen.EDIT})
    @PostMapping("/copy/{code}")
    @ApiOperation(value = "复制大屏/组件", position = 50, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> copy(@PathVariable String code) {
        PageEntity bigScreenPage = bigScreenPageService.getByCode(code);
        if (bigScreenPage == null) {
            throw new GlobalException("大屏页不存在");
        }
        String newCode = bigScreenPageService.copy(bigScreenPage);
        return R.success(newCode);
    }

    @ScreenPermission(permissions = {Permission.Screen.EDIT})
    @PostMapping("/add/template")
    @ApiOperation(value = "从模板新增大屏页", position = 20, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> addByTemplate(@RequestBody BigScreenPageDTO bigScreenPageDTO) {
        String code = bigScreenPageService.addByTemplate(bigScreenPageDTO);
        if (StringUtils.isBlank(bigScreenPageDTO.getParentCode())) {
            bigScreenPageDTO.setParentCode("0");
        }
        return R.success(code);
    }

    @ScreenPermission(permissions = {Permission.Screen.TEMPLATE_VIEW})
    @PostMapping("/get/template")
    @ApiOperation(value = "根据模板获取配置", position = 20, produces = MediaType.APPLICATION_JSON_VALUE)
    public MixinsResp<BigScreenPageDTO> getByTemplate(@RequestBody BigScreenPageDTO bigScreenPageDTO) {
        BigScreenPageDTO config = bigScreenPageService.getConfigByTemplate(bigScreenPageDTO);
        MixinsResp<BigScreenPageDTO> resp = new MixinsResp<BigScreenPageDTO>().setData(config);
        resp.setCode(BigScreenConst.Response.Code.SUCCESS);
        return resp;
    }


    @ScreenPermission
    @GetMapping("/bg/list")
    @ApiOperation(value = "背景图片列表", position = 60, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<StaticFileVO>> getBgList() {
        List<String> staticFileList = Webjars.BIG_SCREEN_BG;
        List<StaticFileVO> bgList = Lists.newArrayList();
        String urlPrefix = bigScreenConfig.getFile().getUrlPrefix() + "bigScreenBg/";
        for (String fileName : staticFileList) {
            StaticFileVO fileVO = new StaticFileVO();
            fileVO.setUrl(urlPrefix + fileName);
            fileVO.setName(fileName);
            bgList.add(fileVO);
        }
        return R.success(bgList);
    }

    @ScreenPermission
    @GetMapping("/map/list/{level}")
    @ApiOperation(value = "地图数据列表", position = 60, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<StaticFileVO>> getMapJsonList(@PathVariable("level") String level) {
        List<String> staticFileList = Lists.newArrayList();
        if ("country".equals(level)) {
            staticFileList = Webjars.COUNTRY_MAP_DATA;
        }
        if ("province".equals(level)) {
            staticFileList = Webjars.PROVINCE_MAP_DATA;
        }
        List<StaticFileVO> bgList = Lists.newArrayList();
        String urlPrefix = "static/chinaMap/" + level + "/";
        for (String fileName : staticFileList) {
            StaticFileVO fileVO = new StaticFileVO();
            fileVO.setUrl(fileName);
            fileVO.setName(fileName.replace(".json", ""));
            bgList.add(fileVO);
        }
        return R.success(bgList);
    }
}
