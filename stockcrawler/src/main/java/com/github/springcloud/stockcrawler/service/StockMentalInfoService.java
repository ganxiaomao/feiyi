package com.github.springcloud.stockcrawler.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.springcloud.stockcrawler.dbentity.StockDailyCrawlTaskEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.vo.ResultVo;

import java.util.Date;
import java.util.List;

/**
 * 股票基本面信息相关业务逻辑接口
 * Created by ganzhen on 2018/4/28.
 */
public interface StockMentalInfoService extends IService<StockDailyMentalInfoEntity> {

    /**
     * 根据指定的日期，获取符合的数据
     * @param date
     * @return
     */
    public List<StockDailyMentalInfoEntity> getDatasByDate(Date date);

    /**
     * 抓取指定时间的所有上市A股股市的股票基本面数据
     * @param date
     * @return
     */
    public ResultVo crawlStockDailyMentalInfoFromLixinger(Date date);

    /**
     * 调用理杏仁接口获取股票基本面数据
     * @param requestJson
     * @return
     */
    public List<StockDailyMentalInfoEntity> fetchStockMentalInfoFromLixingren(String requestJson);

    /**
     * 创建股票信息日常抓取任务，目前包括：股票基本面数据
     * @param date
     * @return
     */
    public ResultVo createStockDailyCrawlTask(Date date);

    /**
     * 批量保存股票日常信息抓取任务
     * @param entities
     * @return
     */
    public boolean batchInsertStockDailyCrawlTask(List<StockDailyCrawlTaskEntity> entities);

    public ResultVo exeCrawlStockMentalInfoTask();

}
