package com.gccloud.bigscreen.core.module.category.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.bigscreen.core.entity.SuperEntity;
import com.gccloud.bigscreen.core.exception.GlobalException;
import com.gccloud.bigscreen.core.module.category.service.ICategoryService;
import com.gccloud.bigscreen.core.utils.AssertUtils;
import com.gccloud.bigscreen.core.utils.CodeGenerateUtils;
import com.gccloud.bigscreen.core.module.category.dto.CategoryDTO;
import com.gccloud.bigscreen.core.module.category.dto.CategorySearchDTO;
import com.gccloud.bigscreen.core.module.category.vo.CategoryVO;
import com.gccloud.bigscreen.core.constant.PageDesignConstant;
import com.gccloud.bigscreen.core.module.basic.entity.PageEntity;
import com.gccloud.bigscreen.core.module.basic.service.IBasePageService;
import com.gccloud.bigscreen.core.utils.QueryWrapperUtils;
import com.gccloud.bigscreen.core.utils.BeanConvertUtils;
import com.gccloud.bigscreen.core.module.basic.dao.PageDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 目录业务
 *
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<PageDao, PageEntity> implements ICategoryService, IBasePageService {

    public List<PageEntity> getByAppCode(CategorySearchDTO searchDTO) {
        LambdaQueryWrapper<PageEntity> queryWrapper = QueryWrapperUtils.wrapperLike(new LambdaQueryWrapper<>(), searchDTO.getSearchKey(), PageEntity::getName);
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getAppCode()), PageEntity::getAppCode, searchDTO.getAppCode());
        queryWrapper.in(!CollectionUtils.isEmpty(searchDTO.getTypeList()), PageEntity::getType, searchDTO.getTypeList());
        queryWrapper.orderByAsc(PageEntity::getOrderNum);
        return this.list(queryWrapper);
    }

    @Override
    public List<CategoryVO> getTree(CategorySearchDTO searchDTO) {
        List<PageEntity> list = getByAppCode(searchDTO);
        if (StringUtils.isNotBlank(searchDTO.getExcludeCategory())) {
            list.removeIf(pageEntity -> StringUtils.equals(pageEntity.getCode(), searchDTO.getExcludeCategory()));
        }
        list.sort(Comparator.comparing(SuperEntity::getCreateDate));
        List<CategoryVO> voList = Lists.newArrayList();
        for (PageEntity categoryEntity : list) {
            CategoryVO vo = BeanConvertUtils.convert(categoryEntity, CategoryVO.class);
            voList.add(vo);
        }
        List<CategoryVO> rootVoList = transToTree(voList, searchDTO.getSort(), StringUtils.isBlank(searchDTO.getSearchKey()));
        return rootVoList;
    }

    @Override
    public List<CategoryVO> getList(CategorySearchDTO searchDTO) {
        List<PageEntity> list = getByAppCode(searchDTO);
        List<CategoryVO> voList = Lists.newArrayList();
        list.sort(Comparator.comparing(PageEntity::getOrderNum));
        for (PageEntity categoryEntity : list) {
            CategoryVO vo = BeanConvertUtils.convert(categoryEntity, CategoryVO.class);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void deleteByCode(String code) {
        LambdaQueryWrapper<PageEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageEntity::getParentCode, code);
        if (count(queryWrapper) > 0) {
            throw new GlobalException("存在子节点，不允许删除");
        }
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageEntity::getCode, code);
        delete(queryWrapper);
    }

    @Override
    public String add(CategoryDTO categoryDTO) {
        if (StringUtils.isBlank(categoryDTO.getCode())) {
            categoryDTO.setCode(CodeGenerateUtils.generate("category"));
        }
        PageEntity categoryEntity = BeanConvertUtils.convert(categoryDTO, PageEntity.class);
        AssertUtils.isTrue(!checkNameRepeat(categoryEntity), "名称重复");
        AssertUtils.isTrue(!checkCodeRepeat(categoryEntity), "编码重复");
        this.save(categoryEntity);
        return categoryEntity.getCode();
    }

    @Override
    public String update(CategoryDTO categoryDTO) {
        AssertUtils.isTrue(StringUtils.isNotBlank(categoryDTO.getCode()), "编码不能为空");
        AssertUtils.isTrue(StringUtils.isNotBlank(categoryDTO.getId()), "ID不能为空");
        PageEntity categoryEntity = BeanConvertUtils.convert(categoryDTO, PageEntity.class);
        AssertUtils.isTrue(!checkNameRepeat(categoryEntity), "名称重复");
        AssertUtils.isTrue(!checkCodeRepeat(categoryEntity), "编码重复");
        this.updateById(categoryEntity);
        return categoryEntity.getCode();
    }


    /**
     * 将列表转换为树形结构
     *
     * @param voList
     * @param sort
     * @return
     */
    private List<CategoryVO> transToTree(List<CategoryVO> voList, boolean sort, boolean search) {
        Map<String, CategoryVO> voMap = Maps.newHashMap();
        voList.forEach(vo -> voMap.put(vo.getCode(), vo));
        List<CategoryVO> rootList = Lists.newArrayList();
        // 转为树
        for (CategoryVO vo : voList) {
            switch (vo.getType()) {
                case PageDesignConstant.Type.CATALOG:
                    vo.setSort(1);
                    break;
                case PageDesignConstant.Type.BIG_SCREEN:
                    vo.setSort(2);
                    break;
                default:
                    vo.setSort(5);
            }
            if (StringUtils.isBlank(vo.getParentCode()) || "0".equals(vo.getParentCode())) {
                rootList.add(vo);
                continue;
            }
            CategoryVO parentVo = voMap.get(vo.getParentCode());
            if (parentVo == null) {
                continue;
            }
            List<CategoryVO> children = parentVo.getChildren();
            if (children == null) {
                children = Lists.newArrayList();
                parentVo.setChildren(children);
            }
            children.add(vo);
        }
        this.handleSort(rootList);
        return rootList;
    }

    private void handleSort(List<CategoryVO> list) {
        // 排序,升序
        list.sort(Comparator.comparing(CategoryVO::getOrderNum));
        for (CategoryVO vo : list) {
            if (vo.getChildren() != null && vo.getChildren().size() != 0) {
                this.handleSort(vo.getChildren());
            }
        }
    }

}
