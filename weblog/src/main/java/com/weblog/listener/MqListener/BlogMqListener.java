package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.service.IBlogService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.joda.time.LocalTime;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/11
 */
@ApiModel(description = "Blog博文的Mq监听器")
@Component
public class BlogMqListener {

    @Autowired
    private IBlogService blogService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate1(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate2(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate3(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate4(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate5(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate6(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate7(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate8(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate9(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate10(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }


//    ---------------------------------

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate11(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate12(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate13(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate14(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate15(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate16(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate17(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate18(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate19(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate20(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }


    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate21(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate22(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate23(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate24(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate25(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate26(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate27(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate28(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate29(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate30(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }


//    ---------------------------------

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate31(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate32(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate33(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate34(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate35(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate36(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate37(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate38(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate39(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById(message);

            System.out.println("-----------【队列1】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = BlogMqConstants.BLOG_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate40(String message) {
        if (StringUtils.isNotBlank( message )){
            // 更新es索引库中的blog
            blogService.insertBlogToIndexById( message );

            System.out.println("-----------【队列2】 BLOG_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_INSERT_QUEUE,message);
        }
    }














    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = BlogMqConstants.BLOG_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            blogService.deleteBlogOfIndexByBlogId( message );
            System.out.println("-----------【队列1】 BLOG_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",BlogMqConstants.BLOG_DELETE_QUEUE,message);
        }
    }

    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = BlogMqConstants.BLOG_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            blogService.deleteBlogOfIndexByBlogId( message );
            System.out.println("-----------【队列2】 BLOG_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",BlogMqConstants.BLOG_DELETE_QUEUE,message);
        }
    }

}
