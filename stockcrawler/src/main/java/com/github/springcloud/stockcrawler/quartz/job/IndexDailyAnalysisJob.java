package com.github.springcloud.stockcrawler.quartz.job;

import com.github.springcloud.stockcrawler.quartz.BaseJob;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 指数每日分析job
 */
public class IndexDailyAnalysisJob implements BaseJob {
    private static Logger _log = LoggerFactory.getLogger(IndexDailyAnalysisJob.class);

    public IndexDailyAnalysisJob(){}

    @Autowired
    private StockCrawlerService stockCrawlerServiceImpl;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        _log.info("开始每日指数分析任务，当前时间："+System.currentTimeMillis());
        stockCrawlerServiceImpl.quartzTest();//.crawlStockBaseInfo();
    }
}
