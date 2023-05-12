package com.gccloud.starter.common.config;

import com.gccloud.starter.common.config.bean.Cors;
import com.gccloud.starter.common.config.bean.FileConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 基础框架基础配置
 *
 * @Author maoshufeng
 * @Date 2020-06-16
 * @Version 1.0.0
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "gc.starter")
@Data
public class GlobalConfig {

    /**
     * 文件存储配置
     */
    @NestedConfigurationProperty
    private FileConfig file = new FileConfig();

    /**
     * 跨域设置
     */
    @NestedConfigurationProperty
    private Cors cors = new Cors();

}
