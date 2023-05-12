package com.gccloud.starter.lowcode.common.utils;

import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.dictionary.py.PinyinDictionary;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 编码生成
 */
public class CodeGenerateUtils {

    /**
     * 根据名称统一生成编码
     *
     * @param prefix 前缀
     * @return
     */
    public static String generate(String prefix) {
        if ("app".equals(prefix)) {
            return prefix + "-" + RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        }
        return prefix + "_" + RandomStringUtils.randomAlphanumeric(10);
    }

    /**
     * 根据文本生成编码
     *
     * @param text      模板文本
     * @param separator 生成的字符串分隔符
     * @param randomNum 随机字符串个数
     * @return
     */
    public static String generate(String text, String separator, int randomNum) {
        List<Pinyin> pinyinList = PinyinDictionary.convertToPinyin(text, true);
        int length = pinyinList.size();
        StringBuilder sb = new StringBuilder(length * (5 + separator.length()));
        int i = 1;
        for (Pinyin pinyin : pinyinList) {
            if (pinyin == Pinyin.none5) {
                // sb.append(text.charAt(i - 1));
                continue;
            } else {
                String str = pinyin.getPinyinWithoutTone();
                sb.append(StringUtils.capitalize(str));
            }
            if (i < length) {
                sb.append(separator);
            }
            ++i;
        }
        if (randomNum > 0) {
            // 添加随机数
            sb.append(separator + RandomStringUtils.randomAlphanumeric(randomNum));
        }
        if (StringUtils.isBlank(sb)) {
            return RandomStringUtils.randomAlphanumeric(6);
        }
        return sb.toString();
    }

    /**
     * 中文转拼音，其他字符不变
     *
     * @param text        文本
     * @param remainOther 是否保留其他字符
     * @return
     */
    public static String toPinYin(String text, boolean remainOther) {
        char[] chars = text.toCharArray();
        List<Pinyin> pinyinList = PinyinDictionary.convertToPinyin(text, remainOther);
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Pinyin pinyin : pinyinList) {
            if (pinyin == Pinyin.none5) {
                sb.append(chars[i]);
                i++;
                continue;
            }
            sb.append(pinyin.getPinyinWithoutTone());
            i++;
        }
        return sb.toString();
    }

}
