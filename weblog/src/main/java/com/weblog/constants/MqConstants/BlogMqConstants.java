package com.weblog.constants.MqConstants;

import io.swagger.annotations.ApiModel;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "定义blog交换机的常量")
public class BlogMqConstants {

    /**
     * 交换机
     */
    public final static String BLOG_EXCHANGE = "blog.topic";

    /**
     * 监听新增和修改的队列
     */
    public final static String BLOG_INSERT_QUEUE = "blog.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String BLOG_DELETE_QUEUE = "blog.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String BLOG_INSERT_KEY = "blog.insert";

    /**
     * 删除的RoutingKey
     */
    public final static String BLOG_DELETE_KEY = "blog.delete";


}
