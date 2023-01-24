package com.weblog.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@Slf4j
@Configuration
public class MqCorrelationDate {

    public static CorrelationData getCorrelationData() {

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
        return correlationData;
    }




}
