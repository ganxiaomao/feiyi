package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.MyMapper;
import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.JobAndTrigger;

import java.util.List;

/**
 * Created by ganzhen on 03/04/2018.
 */
public interface JobAndTriggerMapper extends SuperMapperPlus<JobAndTrigger> {
    public List<JobAndTrigger> getJobAndTriggerDetails();
}
