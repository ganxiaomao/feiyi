package com.github.springcloud.stockcrawler.service;

import com.github.pagehelper.PageInfo;
import com.github.springcloud.stockcrawler.dbentity.JobAndTrigger;

/**
 * Created by ganzhen on 22/02/2018.
 */
public interface QuartzService {
    /**
     * 增加job录入到mysql中
     * @param jobClassName
     * @param jobGroupName
     * @param cornExpression
     * @return
     */
    public String addJob2Mysql(String jobClassName, String jobGroupName, String cornExpression);

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
