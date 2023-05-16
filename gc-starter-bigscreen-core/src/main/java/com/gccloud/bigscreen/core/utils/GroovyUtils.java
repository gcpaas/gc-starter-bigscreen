package com.gccloud.bigscreen.core.utils;

import com.gccloud.bigscreen.core.exception.GlobalException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.codehaus.groovy.runtime.InvokerHelper;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 函数执行
 *
 * @author liuchengbiao
 * @date 2019-07-14 21:15
 */
@Slf4j
public class GroovyUtils {
    /**
     * 缓存编译好的类
     * key: 脚本
     * value: 编译好的class
     */
    static final Cache<String, Class> CACHE_CLASS = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();

    /**
     * 执行函数
     *
     * @param groovyScript 传入的脚本
     * @param params       传入的参数
     */
    public static Object run(String groovyScript, Map<String, Object> params) {
        Class clazz = buildClass(groovyScript);
        if (clazz == null) {
            return null;
        }
        Binding binding = new Binding();
        // 设置变量
        Map variables = binding.getVariables();
        if (params != null) {
            variables.putAll(params);
        }
        try {
            Script script = InvokerHelper.createScript(clazz, binding);
            Object result = script.run();
            return result;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            throw new GlobalException("脚本执行失败", e);
        }
    }

    /**
     * 脚本编译
     *
     * @param groovyScript
     * @return
     */
    public static Class buildClass(String groovyScript) {
        if (StringUtils.isBlank(groovyScript)) {
            return null;
        }
        Class clazz = CACHE_CLASS.get(groovyScript, (script) -> {
            ClassLoader parent = Thread.currentThread().getContextClassLoader();
            GroovyClassLoader loader = null;
            try {
                loader = new GroovyClassLoader(parent);
                return loader.parseClass(script);
            } catch (Exception e) {
                log.error("脚本 {} 编译失败:{}", script, e);
            } finally {
                if (loader != null) {
                    try {
                        loader.close();
                    } catch (Exception e) {
                        log.error(ExceptionUtils.getStackTrace(e));
                    }
                }
            }
            return null;
        });
        return clazz;
    }
}
