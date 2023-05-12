package com.gccloud.starter.common.module.file.dto;

import com.gccloud.starter.common.dto.SearchDTO;
import lombok.Data;

@Data
public class FileSearchDTO extends SearchDTO {

    private String module;
}
