package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "定义comment交换机的常量")
public class CommentMqConstants {

    /**
     * 交换机
     */
    public final static String COMMENT_EXCHANGE = "comment.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String COMMENT_INSERT_QUEUE = "comment.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String COMMENT_DELETE_QUEUE = "comment.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String COMMENT_INSERT_KEY = "comment.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String COMMENT_DELETE_KEY = "blog.delete";


}
