package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "定义 reply 交换机的常量")
public class ReplyMqConstants {

    /**
     * 交换机
     */
    public final static String REPLY_EXCHANGE = "reply.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String REPLY_INSERT_QUEUE = "reply.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String REPLY_DELETE_QUEUE = "reply.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String REPLY_INSERT_KEY = "reply.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String REPLY_DELETE_KEY = "reply.delete";


}
