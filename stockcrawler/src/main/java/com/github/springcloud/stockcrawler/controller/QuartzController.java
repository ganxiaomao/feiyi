package com.github.springcloud.stockcrawler.controller;

import com.github.springcloud.stockcrawler.quartz.BaseJob;
import com.github.springcloud.stockcrawler.service.QuartzService;
import com.google.common.base.Strings;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ganzhen on 22/02/2018.
 */
@RestController
@RequestMapping("quartz")
public class QuartzController {
    Logger logger = LoggerFactory.getLogger(QuartzController.class);

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @Autowired
    private QuartzService quartzService;

    @RequestMapping(value="addjob",method= RequestMethod.GET)
    public ResponseEntity<?> addQuartzJob(
            @RequestParam(value="jobClassName") String jobClassName,
            @RequestParam(value="jobGroupName") String jobGroupName,
            @RequestParam(value="cronExpression") String cronExpression){
        boolean res = true;
        if(!Strings.isNullOrEmpty(jobClassName) && !Strings.isNullOrEmpty(jobGroupName) && !Strings.isNullOrEmpty(cronExpression)){
            //
            String id = "";
            try {
                //先保存
                id = quartzService.addJob2Mysql(jobClassName,jobGroupName,cronExpression);
                if(!Strings.isNullOrEmpty(id)){
                    //启动调度器
                    scheduler.start();
                    //构建job信息
                    JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName,jobGroupName).build();
                    //表达式调度构建起（任务执行的时间）
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
                    //按照新的表达式，构建一个新的trigger
                    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobClassName,jobGroupName).withSchedule(scheduleBuilder).build();
                    //添加任务
                    scheduler.scheduleJob(cronTrigger);
                }
            } catch (Exception e) {
                res = false;
                logger.info("添加定时任务失败:",e.getCause());
            }
        }
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value="deleteajob",method = RequestMethod.GET)
    public ResponseEntity deleteAJob(
            @RequestParam(value="jobClassName") String jobClassName,
            @RequestParam(value="jobGroupName") String jobGroupName){
        boolean res = true;
        if(!Strings.isNullOrEmpty(jobClassName) && !Strings.isNullOrEmpty(jobGroupName)){
            try {
                scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName,jobGroupName));
                scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName,jobGroupName));
                scheduler.deleteJob(JobKey.jobKey(jobClassName,jobGroupName));
            } catch (Exception e) {
                res = false;
                logger.info("删除定时任务失败:",e.getCause());
            }
        }
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value="pausejob",method = RequestMethod.GET)
    public ResponseEntity pauseJob(@RequestParam(value="jobClassName") String jobClassName,
                                   @RequestParam(value="jobGroupName") String jobGroupName){
        boolean res = true;
        try {
            scheduler.pauseJob(JobKey.jobKey(jobClassName,jobGroupName));
        } catch (SchedulerException e) {
            res = false;
            logger.info("暂停定时任务失败:",e.getCause());
        }
        return ResponseEntity.ok(res);
    }

    @RequestMapping(value="reschedulejob",method = RequestMethod.GET)
    public ResponseEntity rescheduleJob(
            @RequestParam(value="jobClassName") String jobClassName,
            @RequestParam(value="jobGroupName") String jobGroupName,
            @RequestParam(value="cronExpression") String cronExpression){
        boolean res = true;
        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName,jobGroupName);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey,trigger);
        }
        catch(Exception e){
            res = false;
            logger.info("更新定时任务失败:",e.getCause());
        }
        return ResponseEntity.ok(res);
    }

    public static BaseJob getClass(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> class1 = Class.forName(className);
        return (BaseJob)class1.newInstance();
    }
}
