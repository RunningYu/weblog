package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.ReplyMqConstants;
import com.weblog.service.IBlogService;
import com.weblog.service.ICommentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "回复的Mq监听器")
@Component
public class ReplyMqListener {

    @Autowired
    private ICommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate1(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate2(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate4(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate5(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate6(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate7(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate8(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate9(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate10(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate11(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate12(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate14(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate15(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate16(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate17(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate18(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate19(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate20(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate21(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate22(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate24(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate25(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate26(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate27(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate28(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate29(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate30(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate31(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate32(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate34(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate35(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate36(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate37(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate38(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate39(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列2】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = ReplyMqConstants.REPLY_INSERT_QUEUE)
    public void listenReplyInsertOrUpdate40(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertReplyToIndexByReplyId(message);
            System.out.println("-----------【队列1】 REPLY_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听3（String） {} 中消息： {}",ReplyMqConstants.REPLY_INSERT_QUEUE,message);
        }
    }






    @ApiOperation("监听 删除 的业务")
    @RabbitListener(queues = ReplyMqConstants.REPLY_DELETE_QUEUE)
    @Async
    public void listenReplyDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            commentService.deleteReplyOfIndexByReplyKeyId(message);
            System.out.println("-----------【队列1】 REPLY_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",ReplyMqConstants.REPLY_DELETE_QUEUE,message);
        }
    }

    @ApiOperation("监听 删除 的业务")
    @RabbitListener(queues = ReplyMqConstants.REPLY_DELETE_QUEUE)
    @Async
    public void listenReplyDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            commentService.deleteReplyOfIndexByReplyKeyId(message);
            System.out.println("-----------【队列2】 REPLY_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",ReplyMqConstants.REPLY_DELETE_QUEUE,message);
        }
    }

}
