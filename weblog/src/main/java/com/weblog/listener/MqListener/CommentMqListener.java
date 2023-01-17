package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.CommentMqConstants;
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
@ApiModel(description = "评论的Mq监听器")
@Component
public class CommentMqListener {

    @Autowired
    private ICommentService commentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate1(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate2(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate3(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate4(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate5(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate6(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate7(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate8(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate9(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate10(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate11(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate12(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate13(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate14(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate15(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate16(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate17(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate18(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate19(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate20(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate21(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate22(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate23(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate24(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate25(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate26(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate27(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate28(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate29(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate30(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate31(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate32(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate33(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate34(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate35(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate36(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate37(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate38(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate39(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列1】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate40(String message) {
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }







    @ApiOperation("监听 删除 的业务")
    @RabbitListener(queues = CommentMqConstants.COMMENT_DELETE_QUEUE)
    @Async
    public void listenCommentDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            commentService.deleteCommentOfIndexByCommentKeyId(message);
            System.out.println("-----------【队列1】 COMMENT_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_DELETE_QUEUE,message);
        }
    }

    @ApiOperation("监听 删除 的业务")
    @RabbitListener(queues = CommentMqConstants.COMMENT_DELETE_QUEUE)
    @Async
    public void listenCommentDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            commentService.deleteCommentOfIndexByCommentKeyId(message);
            System.out.println("-----------【队列2】 COMMENT_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_DELETE_QUEUE,message);
        }
    }

}
