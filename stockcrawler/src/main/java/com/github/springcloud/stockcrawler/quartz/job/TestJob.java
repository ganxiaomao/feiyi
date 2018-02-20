package com.github.springcloud.stockcrawler.quartz.job;

import com.github.springcloud.stockcrawler.quartz.BaseJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob implements BaseJob {
    private static Logger _log = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
