package com.weblog.listener.MqListener;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.weblog.constants.MqConstants.BlogMqConstants;
import com.weblog.constants.MqConstants.CaffeineMqConstants;
import com.weblog.constants.other.CaffeineConstants;
import com.weblog.service.IBlogService;
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
 * @date : 2022/12/15
 */
@ApiModel(description = "缓存的Mq监听器")
@Component
public class CaffeineMqListener {

    @Autowired
    private IBlogService blogService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(" 异步处理&MQ监听整合, 监听 缓存 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CaffeineMqConstants.CAFFEINE_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate1(String message) {
        System.out.println(" 缓存Mq  1");
        if (StringUtils.isNotBlank( message )){
            if (message.equals(CaffeineConstants.HOT_BLOG_KEY)) {

                System.out.println(" 缓存Mq 2");

                // 更新caffeine 和 redis 中对热榜博文的缓存
                blogService.updateBlogInCaffeineAndRedis();
            }

            System.out.println("-----------【队列1】 CAFFEINE_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CaffeineMqConstants.CAFFEINE_INSERT_QUEUE,message);
        }
    }

    @ApiOperation(" 异步处理&MQ监听整合, 监听 缓存 新增 or 修改 的业务")
    @Async
    @RabbitListener(queues = CaffeineMqConstants.CAFFEINE_INSERT_QUEUE)
    public void listenBlogInsertOrUpdate2(String message) {

        System.out.println(" 缓存Mq 1");

        if (StringUtils.isNotBlank( message )){
            System.out.println(" 缓存Mq 2");
            if (message.equals(CaffeineConstants.HOT_BLOG_KEY)) {
                // 更新caffeine 和 redis 中对热榜博文的缓存
                blogService.updateBlogInCaffeineAndRedis();
            }

            System.out.println("-----------【队列2】 CAFFEINE_INSERT_QUEUE 同步新增 or 修改了--->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CaffeineMqConstants.CAFFEINE_INSERT_QUEUE,message);
        }
    }

    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = CaffeineMqConstants.CAFFEINE_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate1(String message){
        if (StringUtils.isNotBlank( message )){

            if (message.equals(CaffeineConstants.HOT_BLOG_KEY)) {
                // 删除 caffeine 和 redis 中的key
                blogService.deleteKeyOfCaffeineAndRedis(message);
            }
            System.out.println("-----------【队列1】 CAFFEINE_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听1（String） {} 中消息： {}",CaffeineMqConstants.CAFFEINE_DELETE_QUEUE,message);
        }
    }

    @ApiOperation("监听 博文的 删除 的业务")
    @RabbitListener(queues = CaffeineMqConstants.CAFFEINE_DELETE_QUEUE)
    @Async
    public void listenDiaryDeleteOrUpdate2(String message){
        if (StringUtils.isNotBlank( message )){
            if (message.equals(CaffeineConstants.HOT_BLOG_KEY)) {
                // 删除 caffeine 和 redis 中的key
                blogService.deleteKeyOfCaffeineAndRedis(message);
            }
            System.out.println("-----------【队列2】 CAFFEINE_DELETE_QUEUE --->" + LocalTime.now());
            logger.info("Token时效队列 监听2（String） {} 中消息： {}",CaffeineMqConstants.CAFFEINE_DELETE_QUEUE,message);
        }
    }

}
