package com.gccloud.bigscreen.core.constant;

/**
 * @author liuchengbiao
 * @date 2020-06-21 13:33
 */
public interface BigScreenConst {
    /**
     * 控制台
     */
    interface Console {
        /**
         * 控制台line
         */
        String LINE = "----------------------------------------";
    }

    /**
     * 删除标志位
     */
    interface DelFlag {
        /**
         * 删除
         */
        int DELETE = 1;
        /**
         * 未删除
         */
        int NOAMAL = 0;
    }

    /**
     * 响应
     */
    interface Response {
        /**
         * 响应码
         */
        interface Code {
            /**
             * 未登录
             */
            int NO_LOGIN = 401;
            /**
             * 无权限
             */
            int NO_ACCESS = 403;
            /**
             * 资源不存在
             */
            int NO_FOUNT = 404;
            /**
             * 服务端异常
             */
            int SERVER_ERROR = 500;
            /**
             * 成功
             */
            int SUCCESS = 200;
        }
    }

}
