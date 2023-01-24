package com.weblog;

import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.UserMqConstants;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/21
 */
@ApiModel(description = "RabbitMq的测试类")
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimoleQueue() throws InterruptedException {
        // 1. 准备消息
        String message = "hello, spring amqp!";





        // 2. 准备 CorrelationDate
        // 2.1 消息ID
        CorrelationData correlationData = new CorrelationData( UUID.randomUUID().toString() );

        // 2.2. 准备ConfirmCallback
        correlationData.getFuture().addCallback(result -> {   //成功回调
            // 判断结果
            if( result.isAck() ) {
                // ACK
                log.debug( "消息成功投递到交换机！消息ID：{}", correlationData.getId() );
            } else {
                //NACK
                log.error( "消息投递到交换机失败！消息ID：{}", correlationData.getId() );
                // 重发消息
            }
        }, ex -> {   //失败回调
            //记录日志
            log.error( "消息发送失败！", ex );
        });

        // 3. 发送消息
        rabbitTemplate.convertAndSend(UserMqConstants.USER_EXCHANGE, UserMqConstants.USER_INSERT_KEY, 1,correlationData );
    }

}
