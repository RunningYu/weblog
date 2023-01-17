package com.weblog.common.quartz.jobs;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/17
 */

import com.weblog.service.IUserService;
import io.swagger.annotations.ApiModel;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @DisallowConcurrentExecution : 禁止并发地执行通过一个 job 定义（JobDetail定义的）的多个实例
 *
 * @PersistJobDataAfterExecution : 持久化 JobDetail 中的 JobDataMap (对 trigger 中的 datamap 无效)
 *  如果一个任务不是持久化的，则当没有触发器关联它的时候，Quartz会从scheduler中删除它
 */
@ApiModel(description = "更新用户总排名")
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class UpdateUserRankingJob extends QuartzJobBean {

    @Autowired
    private IUserService userService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        userService.updateUserRanking();
    }
}
