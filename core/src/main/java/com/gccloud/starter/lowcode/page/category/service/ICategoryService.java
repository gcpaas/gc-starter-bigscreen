package com.gccloud.starter.lowcode.page.category.service;

import com.gccloud.starter.common.mybatis.service.ISuperService;
import com.gccloud.starter.lowcode.page.category.dto.CategoryDTO;
import com.gccloud.starter.lowcode.page.category.dto.CategorySearchDTO;
import com.gccloud.starter.lowcode.page.category.vo.CategoryVO;
import com.gccloud.starter.lowcode.page.entity.PageEntity;

import java.util.List;

/**
 * 目录
 * @author liuchengbiao
 * @version 1.0
 * @date 2022/8/8 15:11
 */
public interface ICategoryService extends ISuperService<PageEntity> {
    /**
     * 获取目录树
     *
     * @param searchDTO
     * @return
     */
    List<CategoryVO> getTree(CategorySearchDTO searchDTO);


    /**
     * 列表
     * @param searchDTO
     * @return
     */
    List<CategoryVO> getList(CategorySearchDTO searchDTO);

    /**
     * 根据code删除
     *
     * @param code
     */
    void deleteByCode(String code);

    /**
     * 新增
     *
     * @param categoryDTO
     * @return
     */
    String add(CategoryDTO categoryDTO);

    /**
     * 更新目录
     *
     * @param categoryDTO
     * @return
     */
    String update(CategoryDTO categoryDTO);
}
