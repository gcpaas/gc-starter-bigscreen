package com.gccloud.bigscreen.core.module.manage.dto;

import com.gccloud.bigscreen.core.dto.SearchDTO;
import lombok.Data;

@Data
public class BigScreenSearchDTO extends SearchDTO {

    private String parentCode;
}
