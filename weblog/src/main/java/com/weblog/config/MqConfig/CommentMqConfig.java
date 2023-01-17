package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.CommentMqConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "回复的MQ配置")
@Configuration
public class CommentMqConfig {

    @ApiOperation("实现交换机的定义")
    @Bean
    public TopicExchange commentTopicExchange() {
        return new TopicExchange(CommentMqConstants.COMMENT_EXCHANGE, true, false);
    }

    @ApiOperation("定义新增队列")
    @Bean
    public Queue commentInsertQueue(){
        return new Queue(CommentMqConstants.COMMENT_INSERT_QUEUE,true);
    }

    @ApiOperation("定义删除队列")
    @Bean
    public Queue commentDeleteQueue(){
        return new Queue(CommentMqConstants.COMMENT_DELETE_QUEUE,true);
    }

    @ApiOperation("定义绑定关系")
    @Bean
    public Binding commentInsertQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(commentInsertQueue()).to(commentTopicExchange()).with(CommentMqConstants.COMMENT_INSERT_KEY);
    }

    @Bean
    public Binding commentDeleteQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(commentDeleteQueue()).to(commentTopicExchange()).with(CommentMqConstants.COMMENT_DELETE_KEY);
    }


}
