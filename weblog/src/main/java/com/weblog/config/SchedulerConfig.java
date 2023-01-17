package com.weblog.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/11/28
 */
@Configuration
public class SchedulerConfig {

    @Bean
    public Scheduler scheduler() {
        Scheduler scheduler1 = null;
        try {
            scheduler1 = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler1;
    }

}
