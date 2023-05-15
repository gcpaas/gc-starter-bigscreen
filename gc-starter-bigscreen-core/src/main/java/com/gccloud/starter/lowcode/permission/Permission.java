package com.gccloud.starter.lowcode.permission;

/**
 * @author hongyang
 * @version 1.0
 * @date 2023/5/15 17:08
 */
public interface Permission {

    /**
     * 大屏页面的权限
     */
    interface Screen {

        /**
         * 大屏页面的访问权限
         */
        String VIEW = "screen:view";

        /**
         * 大屏页面的编辑权限
         */
        String EDIT = "screen:edit";

        /**
         * 大屏页面的删除权限
         */
        String DELETE = "screen:delete";


        /**
         * 大屏页面模板的查看权限
         */
        String TEMPLATE_VIEW = "screen:template:view";

        /**
         * 大屏页面模板的编辑权限
         */
        String TEMPLATE_EDIT = "screen:template:edit";

        /**
         * 大屏页面模板的删除权限
         */
        String TEMPLATE_DELETE = "screen:template:delete";


    }

    interface DataSource {

        /**
         * 数据源的查看权限
         */
        String VIEW = "dataSource:view";

        /**
         * 数据源的编辑权限
         */
        String EDIT = "dataSource:edit";

        /**
         * 数据源的删除权限
         */
        String DELETE = "dataSource:delete";

        /**
         * 数据源的测试权限
         */
        String TEST = "dataSource:test";

    }

    interface DataSet {

        /**
         * 数据集的分类
         */
        String CATEGORY = "dataSet:category";

        /**
         * 数据集的查看权限
         */
        String VIEW = "dataSet:view";

        /**
         * 数据集的编辑权限
         */
        String EDIT = "dataSet:edit";

        /**
         * 数据集的删除权限
         */
        String DELETE = "dataSet:delete";

        /**
         * 数据集的执行权限
         */
        String EXECUTE = "dataSet:execute";

    }

}
