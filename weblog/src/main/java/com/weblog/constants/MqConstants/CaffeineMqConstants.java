package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/15
 */
@ApiModel(description = "缓存MQ常量")
public class CaffeineMqConstants {
    /**
     * 交换机
     */
    public final static String CAFFEINE_EXCHANGE = "caffeine.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String CAFFEINE_INSERT_QUEUE = "caffeine.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String CAFFEINE_DELETE_QUEUE = "caffeine.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String CAFFEINE_INSERT_KEY = "caffeine.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String CAFFEINE_DELETE_KEY = "caffeine.delete";
}
