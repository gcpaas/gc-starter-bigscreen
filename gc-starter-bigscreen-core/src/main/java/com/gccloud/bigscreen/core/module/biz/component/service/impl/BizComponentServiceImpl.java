package com.gccloud.bigscreen.core.module.biz.component.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.bigscreen.core.exception.GlobalException;
import com.gccloud.bigscreen.core.module.biz.component.dto.BizComponentSearchDTO;
import com.gccloud.bigscreen.core.module.biz.component.dao.BizComponentDao;
import com.gccloud.bigscreen.core.module.biz.component.entity.BizComponentEntity;
import com.gccloud.bigscreen.core.module.biz.component.service.IBizComponentService;
import com.gccloud.bigscreen.core.utils.CodeGenerateUtils;
import com.gccloud.bigscreen.core.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/6/5 13:35
 */
@Service
public class BizComponentServiceImpl extends ServiceImpl<BizComponentDao, BizComponentEntity> implements IBizComponentService {

    @Override
    public PageVO<BizComponentEntity> getPage(BizComponentSearchDTO searchDTO) {
        LambdaQueryWrapper<BizComponentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(searchDTO.getName()), BizComponentEntity::getName, searchDTO.getName());
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getGroup()), BizComponentEntity::getType, searchDTO.getGroup());
        return this.page(searchDTO, queryWrapper);
        
    }

    @Override
    public List<BizComponentEntity> getList(BizComponentSearchDTO searchDTO) {
        LambdaQueryWrapper<BizComponentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(searchDTO.getName()), BizComponentEntity::getName, searchDTO.getName());
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getGroup()), BizComponentEntity::getType, searchDTO.getGroup());
        return this.list(queryWrapper);
    }

    @Override
    public BizComponentEntity getInfoByCode(String code) {
        if (StringUtils.isBlank(code)) {
            throw new GlobalException("组件编码不能为空");
        }
        LambdaQueryWrapper<BizComponentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BizComponentEntity::getCode, code);
        List<BizComponentEntity> list = list(queryWrapper);
        if (list.size() == 0) {
            throw new GlobalException("组件不存在");
        }
        return list.get(0);
    }

    @Override
    public String add(BizComponentEntity entity) {
        boolean repeat = this.checkName(null, entity.getName());
        if (repeat) {
            throw new GlobalException("组件名称重复");
        }
        String code = CodeGenerateUtils.generate("bizComponent");
        entity.setCode(code);
        this.save(entity);
        return code;
    }

    @Override
    public void update(BizComponentEntity entity) {
        boolean repeat = this.checkName(entity.getId(), entity.getName());
        if (repeat) {
            throw new GlobalException("组件名称重复");
        }
        this.updateById(entity);
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isBlank(id)) {
            throw new GlobalException("组件id不能为空");
        }
        this.removeById(id);
    }

    @Override
    public boolean checkName(String id, String name) {
        LambdaQueryWrapper<BizComponentEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BizComponentEntity::getName, name);
        if (StringUtils.isNotBlank(id)) {
            queryWrapper.ne(BizComponentEntity::getId, id);
        }
        return this.count(queryWrapper) > 0;
    }
}
