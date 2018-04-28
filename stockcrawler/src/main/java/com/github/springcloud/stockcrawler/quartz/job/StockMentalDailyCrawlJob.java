package com.github.springcloud.stockcrawler.quartz.job;

import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.quartz.BaseJob;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 每日股票基本面数据从理杏仁定时获取的任务，执行时间设定为当日晚上8点，一般当日晚8点能获取前一日的数据，不过如果如果前一日为节假日，那么当日获取的结果是离得最近的一个营业日的数据，所以要注意处理
 * Created by ganzhen on 2018/4/28.
 */
public class StockMentalDailyCrawlJob implements BaseJob {
    private static Logger _log = LoggerFactory.getLogger(StockMentalDailyCrawlJob.class);

    @Autowired
    private StockMentalInfoService stockMentalInfoServiceImpl;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date now = new Date();
        String nowStr = DateUtils.convertDate2String(now,"yyyy-MM-dd");
        _log.info("开始执行"+nowStr+"日的股票基本面信息抓取任务");
        stockMentalInfoServiceImpl.crawlStockDailyMentalInfoFromLixinger(new Date());
        _log.info("结束执行"+nowStr+"日的股票基本面信息抓取任务");
    }
}
