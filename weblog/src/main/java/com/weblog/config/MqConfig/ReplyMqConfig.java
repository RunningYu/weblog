package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.ReplyMqConstants;
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
@ApiModel(description = "回复的配置")
@Configuration
public class ReplyMqConfig {

    @ApiOperation("实现交换机的定义")
    @Bean
    public TopicExchange replyTopicExchange() {
        return new TopicExchange(ReplyMqConstants.REPLY_EXCHANGE, true, false);
    }

    @ApiOperation("定义新增队列")
    @Bean
    public Queue replyInsertQueue(){
        return new Queue(ReplyMqConstants.REPLY_INSERT_QUEUE,true);
    }
    @ApiOperation("定义删除队列")
    @Bean
    public Queue replyDeleteQueue(){
        return new Queue(ReplyMqConstants.REPLY_DELETE_QUEUE,true);
    }

    @ApiOperation("定义绑定关系")
    @Bean
    public Binding replyInsertQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(replyInsertQueue()).to(replyTopicExchange()).with(ReplyMqConstants.REPLY_INSERT_KEY);
    }

    @Bean
    public Binding replyDeleteQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(replyDeleteQueue()).to(replyTopicExchange()).with(ReplyMqConstants.REPLY_DELETE_KEY);
    }


}
