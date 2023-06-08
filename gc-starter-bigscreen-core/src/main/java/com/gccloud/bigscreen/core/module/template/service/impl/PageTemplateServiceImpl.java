package com.gccloud.bigscreen.core.module.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.bigscreen.core.module.basic.dto.BasePageDTO;
import com.gccloud.bigscreen.core.module.chart.components.datasource.DataSetDataSource;
import com.gccloud.bigscreen.core.module.manage.dto.BigScreenPageDTO;
import com.gccloud.bigscreen.core.module.template.dao.PageTemplateDao;
import com.gccloud.bigscreen.core.module.template.dto.PageTemplateSearchDTO;
import com.gccloud.bigscreen.core.module.template.entity.PageTemplateEntity;
import com.gccloud.bigscreen.core.module.template.service.IPageTemplateService;
import com.gccloud.common.exception.GlobalException;
import com.gccloud.common.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/3/20 16:38
 */
@Service
public class PageTemplateServiceImpl extends ServiceImpl<PageTemplateDao, PageTemplateEntity> implements IPageTemplateService {
    @Override
    public PageVO<PageTemplateEntity> getPage(PageTemplateSearchDTO searchDTO) {
        LambdaQueryWrapper<PageTemplateEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getType()), PageTemplateEntity::getType, searchDTO.getType());
        return page(searchDTO, queryWrapper);
    }

    @Override
    public List<PageTemplateEntity> getList(PageTemplateSearchDTO searchDTO) {
        LambdaQueryWrapper<PageTemplateEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getType()), PageTemplateEntity::getType, searchDTO.getType());
        return list(queryWrapper);
    }

    @Override
    public String add(PageTemplateEntity pageTemplate) {
        BasePageDTO config = pageTemplate.getConfig();
        // 清空数据配置
        ((BigScreenPageDTO) config).setId(null);
        ((BigScreenPageDTO) config).getChartList().forEach(chart -> {
            chart.setDataSource(new DataSetDataSource());
            chart.setCode(null);
        });
        this.save(pageTemplate);
        return pageTemplate.getId();
    }

    @Override
    public void deleteByIds(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.removeByIds(ids);
    }

    @Override
    public void update(PageTemplateEntity pageTemplate) {
        if (StringUtils.isBlank(pageTemplate.getId())) {
            throw new GlobalException("id不能为空");
        }
        this.updateById(pageTemplate);
    }
}
