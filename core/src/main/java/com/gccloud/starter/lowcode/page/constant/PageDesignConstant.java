package com.gccloud.starter.lowcode.page.constant;

/**
 * @author zhang.tianming
 */
public interface PageDesignConstant {

    /**
     * 页面类型
     */
    interface Type {
        /**
         * 大屏
         */
        String BIG_SCREEN = "bigScreen";
        /**
         * 目录
         */
        String CATALOG = "catalog";
    }

    /**
     * 大屏
     */
    interface BigScreen {

        /**
         * 组件类型
         */
        interface Type {

            /**
             * 表格
             */
            String TABLES = "tables";

            /**
             * 外链
             */
            String IFRAME = "iframeChart";

            /**
             * 倒计时
             */
            String TIME_COUNT_DOWN = "timeCountDown";

            /**
             * 当前时间
             */
            String CURRENT_TIME = "currentTime";

            /**
             * 文本
             */
            String TEXT = "texts";

            /**
             * 装饰边框
             */
            String BORDER = "border";

            /**
             * 滚动面板
             */
            String SCREEN_SCROLL_BOARD = "screenScrollBoard";

            /**
             * 滚动排行榜
             */
            String SCREEN_SCROLL_RANKING = "screenScrollRanking";
            /**
             * 自定义组件
             */
            String CUSTOM_COMPONENT = "customComponent";

            /**
             * 地图
             */
            String MAP = "map";

            /**
             * 图标
             */
            String SVGS = "svgs";
        }

        interface Operator {
            /**
             * 等于
             */
            String EQUAL = "=";
            /**
             * 不等于
             */
            String NOT_EQUAL = "!=";
            /**
             * 大于
             */
            String GREATER_THAN = ">";
            /**
             * 小于
             */
            String LESS_THAN = "<";
            /**
             * 大于等于
             */
            String GREATER_THAN_OR_EQUAL = ">=";
            /**
             * 小于等于
             */
            String LESS_THAN_OR_EQUAL = "<=";
            /**
             * 在...之中
             */
            String IN = "IN";
            /**
             * 不在...之中
             */
            String NOT_IN = "NOT IN";
            /**
             * 包含
             */
            String LIKE = "LIKE";
            /**
             * 为空
             */
            String IS_NULL = "IS NULL";
            /**
             * 不为空
             */
            String IS_NOT_NULL = "IS NOT NULL";


        }

        interface Aggregate {
            /**
             * 统计
             */
            String COUNT = "COUNT";
            /**
             * 求和
             */
            String SUM = "SUM";
            /**
             * 平均值
             */
            String AVG = "AVG";
            /**
             * 最大值
             */
            String MAX = "MAX";
            /**
             * 最小值
             */
            String MIN = "MIN";
            /**
             * 统计不重复值
             */
            String COUNT_DISTINCT = "COUNT_DISTINCT";
        }
    }


}
