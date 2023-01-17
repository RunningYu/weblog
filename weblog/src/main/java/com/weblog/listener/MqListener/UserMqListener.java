package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.UserMqConstants;
import com.weblog.service.IUserService;
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
@ApiModel(description = "User的Mq监听器")
@Component
public class UserMqListener {

    @Autowired
    private IUserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate1(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate2(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate3(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate4(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate5(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate6(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate7(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate8(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate9(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate10(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate11(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate12(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate13(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate14(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate15(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate16(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate17(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate18(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate19(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate20(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate21(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate22(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate23(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate24(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate25(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate26(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate27(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate28(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate29(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate30(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate31(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate32(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate33(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate34(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate35(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate36(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate37(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate38(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate39(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate40(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate41(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate42(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate43(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate44(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate45(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate46(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate47(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate48(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate49(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列1】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 监听 博文的 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = UserMqConstants.USER_INSERT_QUEUE)
    public void listenUserInsertOrUpdate50(String message) {
        System.out.println("111111");
        if (StringUtils.isNotBlank( message )){
            userService.insertUserToIndexById(message);
            System.out.println("-----------【队列2】 USER_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_INSERT_QUEUE,message);
        }
    }








    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = UserMqConstants.USER_DELETE_QUEUE)
    @Async
    public void listenUserDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){
            userService.deleteUserOfIndexByUserId(message);
            System.out.println("-----------【队列1】 USER_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",UserMqConstants.USER_DELETE_QUEUE,message);
        }
    }

    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = UserMqConstants.USER_DELETE_QUEUE)
    @Async
    public void listenUserDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            userService.deleteUserOfIndexByUserId(message);
            System.out.println("-----------【队列2】 USER_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",UserMqConstants.USER_DELETE_QUEUE,message);
        }
    }

}
