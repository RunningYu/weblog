package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "定义 user 交换机的常量")
public class UserMqConstants {

    /**
     * 交换机
     */
    public final static String USER_EXCHANGE = "user.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String USER_INSERT_QUEUE = "user.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String USER_DELETE_QUEUE = "user.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String USER_INSERT_KEY = "user.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String USER_DELETE_KEY = "user.delete";


}
