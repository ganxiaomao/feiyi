package com.github.springcloud.stockcrawler.quartz.job;

import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.quartz.BaseJob;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 股票基本面信息每日抓取任务的的定时任务。也就是每天都会生成当天的抓取任务。初定为每日下午17：00生成
 * Created by ganzhen on 2018/4/28.
 */
public class StockMentalInfoDailyCreateCrawlTaskJob implements BaseJob {
    private static Logger _log = LoggerFactory.getLogger(StockMentalInfoDailyCreateCrawlTaskJob.class);

    @Autowired
    private StockCrawlerService stockCrawlerServiceImpl;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date now = new Date();
        _log.info("开始生成当日的基本面信息抓取任务", DateUtils.convertDate2String(now,"yyyy-MM-dd HH-mm-ss"));
        //生成当日的基本面信息抓取任务
        stockCrawlerServiceImpl.createDailyStockMentalInfoCrawlTask(now);
        _log.info("结束生成当日的基本面信息抓取任务");
    }
}
