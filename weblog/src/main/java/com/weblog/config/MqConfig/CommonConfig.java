package com.weblog.config.MqConfig;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/21
 *
 * ApplicationContextAware : spring 的bean工厂的通知
 */
@Slf4j
@ApiModel(description = "每个RabbitTemplate只能配置一个ReturnCallback，因此需要在项目启动过程中配置")
@Configuration
public class CommonConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取 RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 设置 ReturnCallback
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

            // 判断是都是延迟消息
            if (message.getMessageProperties().getReceivedDelay() > 0) {
                // 是一个延迟消息，忽略之后的错误提示
                return;
            }

            log.info("消息发送失败，应答码{}，原因{}，交换机{}，路由键{}，消息{}", replyCode, replyText,exchange,routingKey, message.toString() );
        } );
    }
}
