package com.gccloud.bigscreen.core.module.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.bigscreen.core.config.BigScreenConfig;
import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.exception.GlobalException;
import com.gccloud.bigscreen.core.module.basic.dao.PageDao;
import com.gccloud.bigscreen.core.module.basic.entity.PageEntity;
import com.gccloud.bigscreen.core.module.chart.bean.Chart;
import com.gccloud.bigscreen.core.module.chart.components.datasource.DataSetDataSource;
import com.gccloud.bigscreen.core.module.manage.dto.BigScreenPageDTO;
import com.gccloud.bigscreen.core.module.manage.dto.BigScreenSearchDTO;
import com.gccloud.bigscreen.core.module.manage.service.IBigScreenPageService;
import com.gccloud.bigscreen.core.module.template.entity.PageTemplateEntity;
import com.gccloud.bigscreen.core.module.template.service.IPageTemplateService;
import com.gccloud.bigscreen.core.utils.AssertUtils;
import com.gccloud.bigscreen.core.utils.BeanConvertUtils;
import com.gccloud.bigscreen.core.utils.CodeGenerateUtils;
import com.gccloud.bigscreen.core.utils.QueryWrapperUtils;
import com.gccloud.bigscreen.core.vo.PageVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/13 11:05
 */
@Service
@Slf4j
public class BigScreenPageServiceImpl extends ServiceImpl<PageDao, PageEntity> implements IBigScreenPageService {


    @Resource
    private IPageTemplateService pageTemplateService;

    @Resource
    private BigScreenConfig bigScreenConfig;

    @Override
    public String add(BigScreenPageDTO bigScreenPageDTO) {
        if (StringUtils.isBlank(bigScreenPageDTO.getCode())) {
            String code = CodeGenerateUtils.generate("bigScreen");
            bigScreenPageDTO.setCode(code);
        }
        List<Chart> chartList = bigScreenPageDTO.getChartList();
        if (CollectionUtils.isEmpty(chartList)) {
            chartList = Lists.newArrayList();
        }
        for (Chart chart : chartList) {
            if (StringUtils.isNotBlank(chart.getCode())) {
                continue;
            }
            chart.setCode(CodeGenerateUtils.generate(chart.getType() == null ? "chart" : chart.getType()));
        }
        if (StringUtils.isBlank(bigScreenPageDTO.getCoverPicture())) {
            String base64Str = bigScreenPageDTO.getCoverPicture();
            String fileUrl = this.saveCoverPicture(base64Str, bigScreenPageDTO.getCode());
            bigScreenPageDTO.setCoverPicture(fileUrl);
        }
        PageEntity bigScreenEntity = BeanConvertUtils.convert(bigScreenPageDTO, PageEntity.class);
        bigScreenEntity.setConfig(bigScreenPageDTO);
        AssertUtils.isTrue(!checkNameRepeat(bigScreenEntity), "名称重复");
        AssertUtils.isTrue(!checkCodeRepeat(bigScreenEntity), "编码重复");
        this.save(bigScreenEntity);
        return bigScreenEntity.getCode();
    }


    /**
     * 将base64字符串转为图片文件存储
     *
     * @param base64String
     * @param fileName
     * @return
     */
    private String saveCoverPicture(String base64String, String fileName) {
        String fileUrl = "";
        if (StringUtils.isBlank(base64String)) {
            return fileUrl;
        }
        try {
            // 解码base64字符串
            byte[] imageBytes = Base64.getDecoder().decode(base64String.replace("data:image/png;base64,", ""));
            String basePath = bigScreenConfig.getFile().getBasePath();
            // 不是/结尾，加上/
            if (!basePath.endsWith("/") || !basePath.endsWith("\\")) {
                basePath += File.separator;
            }
            // 检查目录是否存在，不存在则创建
            File file = new File(basePath + "cover");
            if (!file.exists()) {
                file.mkdirs();
            }
            // 保存为图片文件
            String filePath = basePath + "cover" + File.separator + fileName + ".png";
            fileUrl = "cover" + File.separator + fileName + ".png";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.write(imageBytes);
            outputStream.close();
            log.info("大屏封面保存至：{}", filePath);
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return fileUrl;
    }

    @Override
    public String addByTemplate(BigScreenPageDTO bigScreenPageDTO) {
        if (StringUtils.isBlank(bigScreenPageDTO.getPageTemplateId())) {
            throw new GlobalException("页面模板不能为空");
        }
        bigScreenPageDTO = getConfigByTemplate(bigScreenPageDTO);
        return add(bigScreenPageDTO);
    }

    @Override
    public BigScreenPageDTO getConfigByTemplate(BigScreenPageDTO bigScreenPageDTO) {
        String pageTemplateId = bigScreenPageDTO.getPageTemplateId();
        PageTemplateEntity pageTemplate = pageTemplateService.getById(pageTemplateId);
        AssertUtils.isTrue(pageTemplate != null, "页面模板不存在");
        AssertUtils.isTrue(Objects.equals(pageTemplate.getType(), PageDesignConstant.Type.BIG_SCREEN), "页面模板类型不正确");
        BigScreenPageDTO config = (BigScreenPageDTO) pageTemplate.getConfig();
        String name = bigScreenPageDTO.getName();
        if (StringUtils.isBlank(name)) {
            int i = 0;
            String newName = pageTemplate.getName() + "副本";
            while (checkNameRepeat(bigScreenPageDTO.getAppCode(), newName, null)) {
                i++;
                newName = pageTemplate.getName() + "副本" + i;
            }
            name = newName;
        }
        config.setName(name);
        config.setCode(bigScreenPageDTO.getCode());
        config.setParentCode(bigScreenPageDTO.getParentCode());
        config.setId(bigScreenPageDTO.getId());
        config.setAppCode(bigScreenPageDTO.getAppCode());
        List<Chart> chartList = config.getChartList();
        if (CollectionUtils.isEmpty(chartList)) {
            chartList = Lists.newArrayList();
        }
        for (Chart chart : chartList) {
            chart.setCode("");
            chart.setDataSource(new DataSetDataSource());
        }
        return config;
    }

    @Override
    public PageVO<PageEntity> getByCategory(BigScreenSearchDTO searchDTO) {
        LambdaQueryWrapper<PageEntity> queryWrapper = QueryWrapperUtils.wrapperLike(new LambdaQueryWrapper<>(), searchDTO.getSearchKey(), PageEntity::getName);
        if (StringUtils.isNotBlank(searchDTO.getParentCode())) {
            queryWrapper.eq(PageEntity::getParentCode, searchDTO.getParentCode());
        }
        queryWrapper.eq(PageEntity::getType, PageDesignConstant.Type.BIG_SCREEN);
        queryWrapper.select(PageEntity::getId, PageEntity::getAppCode, PageEntity::getCode, PageEntity::getName, PageEntity::getParentCode, PageEntity::getCoverPicture, PageEntity::getUpdateDate);
        queryWrapper.orderByDesc(PageEntity::getUpdateDate);
        PageVO<PageEntity> page = page(searchDTO, queryWrapper);
        List<PageEntity> list = page.getList();
        if (list == null || list.isEmpty()) {
            return page;
        }
        String urlPrefix = bigScreenConfig.getFile().getUrlPrefix();
        if (!urlPrefix.endsWith("/")) {
            urlPrefix += "/";
        }
        for (PageEntity pageEntity : list) {
            if (StringUtils.isBlank(pageEntity.getCoverPicture())) {
                continue;
            }
            pageEntity.setCoverPicture(urlPrefix + pageEntity.getCoverPicture().replace("\\", "/"));
        }
        return page;
    }


    @Override
    public void update(BigScreenPageDTO bigScreenPageDTO) {
        AssertUtils.isTrue(StringUtils.isNotBlank(bigScreenPageDTO.getCode()), "编码不能为空");
        AssertUtils.isTrue(StringUtils.isNotBlank(bigScreenPageDTO.getName()), "名称不能为空");
        List<Chart> chartList = bigScreenPageDTO.getChartList();
        if (CollectionUtils.isNotEmpty(chartList)) {
            for (Chart chart : chartList) {
                if (StringUtils.isNotBlank(chart.getCode())) {
                    continue;
                }
                chart.setCode(CodeGenerateUtils.generate(chart.getType() == null ? "chart" : chart.getType()));
            }
        }
        if (StringUtils.isNotBlank(bigScreenPageDTO.getCoverPicture())) {
            String coverPicture = this.saveCoverPicture(bigScreenPageDTO.getCoverPicture(), bigScreenPageDTO.getCode());
            bigScreenPageDTO.setCoverPicture(coverPicture);
        }
        PageEntity bigScreenEntity = BeanConvertUtils.convert(bigScreenPageDTO, PageEntity.class);
        bigScreenEntity.setConfig(bigScreenPageDTO);
        AssertUtils.isTrue(!checkNameRepeat(bigScreenEntity), "名称重复");
        AssertUtils.isTrue(!checkCodeRepeat(bigScreenEntity), "编码重复");
        this.updateById(bigScreenEntity);
    }

    @Override
    public String copy(String sourceCode) {
        PageEntity screenEntity = this.getByCode(sourceCode);
        AssertUtils.isTrue(screenEntity != null, "源大屏页不存在，复制失败");
        BigScreenPageDTO config = (BigScreenPageDTO) screenEntity.getConfig();
        screenEntity.setId(null);
        screenEntity.setCode(CodeGenerateUtils.generate("bigScreen"));
        screenEntity.setName(screenEntity.getName() + "_复制");
        config.setName(screenEntity.getName());
        config.setCode(screenEntity.getCode());
        List<Chart> chartList = config.getChartList();
        for (Chart chart : chartList) {
            chart.setCode(CodeGenerateUtils.generate(chart.getType() == null ? "chart" : chart.getType()));
        }
        this.save(screenEntity);
        return screenEntity.getCode();
    }

    @Override
    public void deleteByCode(String code) {
        LambdaQueryWrapper<PageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageEntity::getCode, code);
        this.remove(queryWrapper);
    }
}
