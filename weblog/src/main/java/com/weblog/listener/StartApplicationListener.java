package com.weblog.listener;

import com.weblog.common.quartz.events.Events;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/11/28
 */
@ApiModel(description = "springboot中的监听器，启动时，将events中的事件启动")
@Slf4j
@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private Events events;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        events.updateUserRanking();
        log.info("启动 updateUserRanking 的触发器");
        events.updateDBtoES();
        log.info("启动 updateDBtoES 的触发器");
    }
}
