package com.github.springcloud.stockcrawler.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.springcloud.stockcrawler.dbdao.JobAndTriggerMapper;
import com.github.springcloud.stockcrawler.dbentity.JobAndTrigger;
import com.github.springcloud.stockcrawler.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ganzhen on 22/02/2018.
 */
@Service("quartzServiceImpl")
public class QuartzServiceImpl implements QuartzService {
    Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;

    @Override
    public String addJob2Mysql(String jobClassName, String jobGroupName, String cornExpression) {
        return null;
    }

    @Override
    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTrigger> page = new PageInfo<>(list);
        return page;
    }
}
