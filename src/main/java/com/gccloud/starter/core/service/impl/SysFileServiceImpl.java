package com.gccloud.starter.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.starter.common.entity.SysFileEntity;
import com.gccloud.starter.common.exception.GlobalException;
import com.gccloud.starter.common.module.file.dto.FileSearchDTO;
import com.gccloud.starter.common.mybatis.page.PageVO;
import com.gccloud.starter.common.mybatis.utils.QueryWrapperUtils;
import com.gccloud.starter.core.dao.SysFileDao;
import com.gccloud.starter.core.service.ISysFileService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 文件管理
 */
@Service
@Slf4j
@ConditionalOnProperty(prefix = "gc.starter.component", name = "ISysFileService", havingValue = "SysFileServiceImpl", matchIfMissing = true)
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements ISysFileService {

    @Override
    public PageVO<SysFileEntity> getPage(FileSearchDTO searchDTO) {
        LambdaQueryWrapper<SysFileEntity> queryWrapper = QueryWrapperUtils.wrapperLike(new LambdaQueryWrapper(), searchDTO.getSearchKey(), SysFileEntity::getOriginalName);
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getModule()), SysFileEntity::getModule, searchDTO.getModule());
        Map<String, String> aliasMap = Maps.newHashMap();
        aliasMap.put("space", "size");
        QueryWrapperUtils.wrapperSort(null, SysFileEntity.class, queryWrapper, searchDTO, aliasMap, SysFileEntity::getOriginalName, SysFileEntity::getCreateDate, SysFileEntity::getSize, SysFileEntity::getDownloadCount);
        return page(searchDTO, queryWrapper);
    }

    @Override
    public void updateDownloadCount(Integer addCount, String fileId) {
        if (addCount <= 0) {
            throw new GlobalException("下载次数不允许为负数或0");
        }
        baseMapper.updateDownloadCount(addCount, fileId);
    }
}
