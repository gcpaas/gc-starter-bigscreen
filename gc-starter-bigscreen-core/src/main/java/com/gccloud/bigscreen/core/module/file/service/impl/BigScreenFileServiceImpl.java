package com.gccloud.bigscreen.core.module.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gccloud.bigscreen.core.exception.GlobalException;
import com.gccloud.bigscreen.core.module.file.dao.BigScreenFileDao;
import com.gccloud.bigscreen.core.module.file.dto.FileSearchDTO;
import com.gccloud.bigscreen.core.module.file.entity.BigScreenFileEntity;
import com.gccloud.bigscreen.core.module.file.service.IBigScreenFileService;
import com.gccloud.bigscreen.core.vo.PageVO;
import com.gccloud.bigscreen.core.utils.QueryWrapperUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文件管理
 */
@Service
@Slf4j
@ConditionalOnProperty(prefix = "gc.starter.component", name = "ISysFileService", havingValue = "BigScreenFileServiceImpl", matchIfMissing = true)
public class BigScreenFileServiceImpl extends ServiceImpl<BigScreenFileDao, BigScreenFileEntity> implements IBigScreenFileService {

    @Override
    public PageVO<BigScreenFileEntity> getPage(FileSearchDTO searchDTO) {
        LambdaQueryWrapper<BigScreenFileEntity> queryWrapper = QueryWrapperUtils.wrapperLike(new LambdaQueryWrapper(), searchDTO.getSearchKey(), BigScreenFileEntity::getOriginalName);
        queryWrapper.eq(StringUtils.isNotBlank(searchDTO.getModule()), BigScreenFileEntity::getModule, searchDTO.getModule());
        Map<String, String> aliasMap = Maps.newHashMap();
        aliasMap.put("space", "size");
        QueryWrapperUtils.wrapperSort(null, BigScreenFileEntity.class, queryWrapper, searchDTO, aliasMap, BigScreenFileEntity::getOriginalName, BigScreenFileEntity::getCreateDate, BigScreenFileEntity::getSize, BigScreenFileEntity::getDownloadCount);
        return page(searchDTO, queryWrapper);
    }

    @Override
    public void updateDownloadCount(Integer addCount, String fileId) {
        if (addCount <= 0) {
            throw new GlobalException("下载次数不允许为负数或0");
        }
        baseMapper.updateDownloadCount(addCount, fileId);
    }

    @Override
    public List<String> getAllExtension() {
        // 去重获取所有文件后缀名
        return baseMapper.getAllExtension();
    }
}
