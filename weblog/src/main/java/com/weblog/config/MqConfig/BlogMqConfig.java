package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.BlogMqConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.TopicExchange;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "博文配置")
@Configuration
public class BlogMqConfig {

    @ApiOperation("实现交换机的定义")
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(BlogMqConstants.BLOG_EXCHANGE, true, false);
    }

    @ApiOperation("定义新增队列")
    @Bean
    public Queue insertQueue(){
        return new Queue(BlogMqConstants.BLOG_INSERT_QUEUE,true);
    }
    @ApiOperation("定义删除队列")
    @Bean
    public Queue deleteQueue(){
        return new Queue(BlogMqConstants.BLOG_DELETE_QUEUE,true);
    }

    @ApiOperation("定义绑定关系")
    @Bean
    public Binding insertQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(BlogMqConstants.BLOG_INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(BlogMqConstants.BLOG_DELETE_KEY);
    }


}
