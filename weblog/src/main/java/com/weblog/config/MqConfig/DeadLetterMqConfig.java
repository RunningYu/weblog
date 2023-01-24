package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.DlMqConstants;
import com.weblog.constants.MqConstants.ErrorMqConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/22
 */
@Configuration
public class DeadLetterMqConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DlMqConstants.DL_EXCHANGE);
    }
    @Bean
    public Queue DlQueue(){
        return new Queue(DlMqConstants.DL_QUEUE);
    }

    @Bean
    public Binding DlQueueBinding(){
//                            绑定     队列         到    交换机                        用的RoutingKey
        return BindingBuilder.bind(DlQueue()).to(directExchange()).with(DlMqConstants.DL_KEY);
    }

//    /**
//     * 首先，定义接收失败消息的交换机、队列极其绑定关系
//     */
//    @Bean
//    public DirectExchange errorMessageExchange() {
//        return new DirectExchange(ErrorMqConstants.ERROR_EXCHANGE);
//    }
//
//    @Bean
//    public Queue errorQueue() {
//        return new Queue(ErrorMqConstants.ERROR_QUEUE);
//    }
//
//    @Bean
//    public Binding errorMessageBinding() {
//        return BindingBuilder.bind( errorQueue() ).to( errorMessageExchange() ).with( ErrorMqConstants.ERROR_KEY );
//    }

}
