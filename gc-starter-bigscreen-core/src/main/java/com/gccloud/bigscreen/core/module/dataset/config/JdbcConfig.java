package com.gccloud.bigscreen.core.module.dataset.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author pan.shun
 * @since 2021/11/13 11:15
 */

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Slf4j
public class JdbcConfig {

    public static String currentUrl;
    private String url;

    @PostConstruct
    public void init() {
        currentUrl = this.url;
        log.info(currentUrl);
    }
}
