package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.CaffeineMqConstants;
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
 * @date : 2022/12/15
 */
@ApiModel(description = "缓存配置")
@Configuration
public class CaffeineMqConfig {
    
    @ApiOperation("实现交换机的定义")
    @Bean
    public TopicExchange caffeineTopicExchange() {
        return new TopicExchange(CaffeineMqConstants.CAFFEINE_EXCHANGE, true, false);
    }

    @ApiOperation("定义新增队列")
    @Bean
    public Queue caffeineInsertQueue(){
        return new Queue(CaffeineMqConstants.CAFFEINE_INSERT_QUEUE,true);
    }

    @ApiOperation("定义删除队列")
    @Bean
    public Queue caffeineDeleteQueue(){
        return new Queue(CaffeineMqConstants.CAFFEINE_DELETE_QUEUE,true);
    }

    @ApiOperation("定义绑定关系")
    @Bean
    public Binding caffeineInsertQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(caffeineInsertQueue()).to(caffeineTopicExchange()).with(CaffeineMqConstants.CAFFEINE_INSERT_KEY);
    }

    @Bean
    public Binding caffeineDeleteQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(caffeineDeleteQueue()).to(caffeineTopicExchange()).with(CaffeineMqConstants.CAFFEINE_DELETE_KEY);
    }
}
