package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.UserMqConstants;
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
@ApiModel(description = "User配置")
@Configuration
public class UserMqConfig {

    @ApiOperation("实现交换机的定义")
    @Bean
    public TopicExchange userTopicExchange() {
        return new TopicExchange(UserMqConstants.USER_EXCHANGE, true, false);
    }

    @ApiOperation("定义新增队列")
    @Bean
    public Queue userInsertQueue(){
        return new Queue(UserMqConstants.USER_INSERT_QUEUE,true);
    }
    @ApiOperation("定义删除队列")
    @Bean
    public Queue userDeleteQueue(){
        return new Queue(UserMqConstants.USER_DELETE_QUEUE,true);
    }

    @ApiOperation("定义绑定关系")
    @Bean
    public Binding userInsertQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(userInsertQueue()).to(userTopicExchange()).with(UserMqConstants.USER_INSERT_KEY);
    }

    @Bean
    public Binding userDeleteQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(userDeleteQueue()).to(userTopicExchange()).with(UserMqConstants.USER_DELETE_KEY);
    }


}
