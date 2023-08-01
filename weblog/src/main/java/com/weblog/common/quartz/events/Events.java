package com.weblog.common.quartz.events;

import com.weblog.common.quartz.jobs.UpdateUserRankingJob;
import com.weblog.common.quartz.jobs.updateDBtoES;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/17
 */
@ApiModel(description = "任务，事件")
@Configuration
public class Events {
    /**
     * 注入调度器
     */
    @Autowired
    private Scheduler scheduler;

    @ApiOperation("更新用户的总排名")
    public void updateUserRanking() {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger1", "group1");
        try {
            // 在调度器里根据triggerKey来取 Trigger触发器, 当调度器其中没有对应的触发器时，就创建一个触发器
            // 这样写好处是可以确保 这个触发器在调度器中是唯一的（而且因为同一个策略中的触发器只需要一个实例就可以了）
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        // 设置每天凌晨4点触发
                        .withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 * * ?"))
                        .startNow()
                        .build();
                //                                      封装job
                JobDetail jobDetail = JobBuilder.newJob(UpdateUserRankingJob.class)
                        .withIdentity("job1", "group1")
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("更新同步")
    public void updateDBtoES() {
        TriggerKey triggerKey = TriggerKey.triggerKey("trigger2", "group2");
        try {
            // 在调度器里根据triggerKey来取 Trigger触发器, 当调度器其中没有对应的触发器时，就创建一个触发器
            // 这样写好处是可以确保 这个触发器在调度器中是唯一的（而且因为同一个策略中的触发器只需要一个实例就可以了）
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        // 设置每天凌晨3点触发
                        .withSchedule(CronScheduleBuilder.cronSchedule("0 0 3 * * ?"))
                        .startNow()
                        .build();
                //                                      封装job
                JobDetail jobDetail = JobBuilder.newJob(updateDBtoES.class)
                        .withIdentity("job2", "group2")
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
