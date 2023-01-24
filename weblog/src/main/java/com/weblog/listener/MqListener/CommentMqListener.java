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
import org.springframework.amqp.core.Message;
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


    @ApiOperation(" 监听 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CommentMqConstants.COMMENT_INSERT_QUEUE)
    public void listenCommentInsertOrUpdate1(String message) {
        logger.info("------comment----------------->消费消费");
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
        logger.info("------comment----------------->消费消费");
        if (StringUtils.isNotBlank( message )){
            commentService.insertCommentToIndextByCommentId(message);
            System.out.println("-----------【队列2】 COMMENT_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_INSERT_QUEUE,message);
        }
    }


//    @ApiOperation("监听 删除 的业务")
//    @RabbitListener(queues = CommentMqConstants.COMMENT_DELETE_QUEUE)
//    @Async
//    public void listenCommentDeleteOrUpdate1(String message){
//        if (StringUtils.isNotBlank( message )){
//            commentService.deleteCommentOfIndexByCommentKeyId(message);
//            System.out.println("-----------【队列1】 COMMENT_DELETE_QUEUE --->" + LocalTime.now());
//            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CommentMqConstants.COMMENT_DELETE_QUEUE,message);
//        }
//    }
//
//    @ApiOperation("监听 删除 的业务")
//    @RabbitListener(queues = CommentMqConstants.COMMENT_DELETE_QUEUE)
//    @Async
//    public void listenCommentDeleteOrUpdate2(String message){
//        if (StringUtils.isNotBlank( message )){
//            commentService.deleteCommentOfIndexByCommentKeyId(message);
//            System.out.println("-----------【队列2】 COMMENT_DELETE_QUEUE --->" + LocalTime.now());
//            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CommentMqConstants.COMMENT_DELETE_QUEUE,message);
//        }
//    }

}
