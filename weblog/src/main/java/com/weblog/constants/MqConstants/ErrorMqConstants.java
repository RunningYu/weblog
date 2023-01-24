package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "消费者失败机制 相关常量")
public class ErrorMqConstants {

    public final static String ERROR_EXCHANGE = "error.direct";
    public final static String ERROR_QUEUE = "error.queue";
    public final static String ERROR_KEY = "error";

}
