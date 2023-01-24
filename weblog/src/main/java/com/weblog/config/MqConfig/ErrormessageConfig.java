package com.weblog.config.MqConfig;

import com.weblog.constants.MqConstants.ErrorMqConstants;
import io.swagger.annotations.ApiModel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/22
 */
@ApiModel(description = "消费者失败消息处理策略")
@Configuration
public class ErrormessageConfig {

    /**
     * 首先，定义接收失败消息的交换机、队列极其绑定关系
     */
    @Bean
    public DirectExchange errorMessageExchange() {
        return new DirectExchange(ErrorMqConstants.ERROR_EXCHANGE);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(ErrorMqConstants.ERROR_QUEUE);
    }

    @Bean
    public Binding errorMessageBinding() {
        return BindingBuilder.bind( errorQueue() ).to( errorMessageExchange() ).with( ErrorMqConstants.ERROR_KEY );
    }

    /**
    定义 RepublicMessageRecover 会 覆盖Spring默认的bean(我们想覆盖spring默认的bean, 重新定义一个bean即可)
     */
    @Bean
    public MessageRecoverer republicMessageRecoverer( RabbitTemplate rabbitTemplate ) {
        return new RepublishMessageRecoverer( rabbitTemplate, ErrorMqConstants.ERROR_EXCHANGE, ErrorMqConstants.ERROR_KEY );
    }

}
