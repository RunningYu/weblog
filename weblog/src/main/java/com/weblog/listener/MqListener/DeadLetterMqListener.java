package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.CommentMqConstants;
import com.weblog.constants.MqConstants.DlMqConstants;
import com.weblog.service.IBlogService;
import com.weblog.service.ICommentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2023/1/22
 */
@ApiModel(description = "Blog博文的Mq监听器")
@Component
public class DeadLetterMqListener {

    @Autowired
    private ICommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = DlMqConstants.DL_QUEUE)
    public void listenCommentDeleteOrUpdate1(String message){
        logger.info("-------> 删除 发送");
        if (StringUtils.isNotBlank( message )){
            commentService.deleteCommentOfIndexByCommentKeyId(message);
            System.out.println("-----------【队列1】 DlMqConstants.DL_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",DlMqConstants.DL_QUEUE,message);
        }
    }

    @ApiOperation("监听 删除 的业务")
    @RabbitListener(queues = DlMqConstants.DL_QUEUE)
    @Async
    public void listenCommentDeleteOrUpdate2(String message){
        logger.info("-------> 删除 发送");
        if (StringUtils.isNotBlank( message )){
            commentService.deleteCommentOfIndexByCommentKeyId(message);
            System.out.println("-----------【队列2】 DlMqConstants.DL_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",DlMqConstants.DL_QUEUE,message);
        }
    }
}
