package com.wjs.common.base.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by panqingqing on 16/5/29.
 */
public abstract class BaseQuartzJobBean extends QuartzJobBean {

    /**
     * @param jobExecutionContext
     * @return
     * @Title: getApplicationContext
     * @Description: 获取上下文信息
     */
    protected ApplicationContext getApplicationContext(JobExecutionContext jobExecutionContext) {
        try {
            return (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
        } catch (SchedulerException e) {
            throw new RuntimeException("获取[" + e.getMessage() + "]失败", e);
        }
    }
}
