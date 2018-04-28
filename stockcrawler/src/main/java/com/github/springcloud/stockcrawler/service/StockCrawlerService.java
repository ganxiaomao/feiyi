package com.github.springcloud.stockcrawler.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.vo.ResultVo;

import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockCrawlerService extends IService<StockBaseInfoEntity>{

    /**
     * 从证券之星网站，抓取所有的A股上市公司的股票基本信息
     * @return
     */
    public ResultVo crawlStockBaseInfo();

    public ResultVo crawlBaiduStockDetail();

    /**
     * 批量保存股票基本信息数据
     * @param entities
     * @return
     */
    public ResultVo stockBaseInfoBatchSave(List<StockBaseInfoEntity> entities);

    public ResultVo baiduStockDetailSave(StockDetailDayRecordEntity entity);

    public void quartzTest();

    /**
     * 从理杏仁网站获取指定日期的股票基本面数据
     * @param stockCode 股票代码
     * @param date 指定日期，'yyyy-MM-dd'格式
     * @return
     */
    public ResultVo crawlDailyStockInfoFromLixingren(String stockCode,String date);

    public boolean updateStrockBaseInfo(StockBaseInfoEntity entity);

    /**
     * 根据stockCode从股票基础信息表获取一条数据
     * @param stockCode
     * @return
     */
    public StockBaseInfoEntity findOneStockBaseInfoByStockCode(String stockCode);

    /**
     * 根据stockCode在证券之星网站获取股票相关公司的主要信息，比如是否退市、上市时间等
     * @param stockCodes
     * @return
     */
    public ResultVo crawlStockStarBriefInfo(List<String> stockCodes);

    /**
     * 获取数据表stock_base_info中存储的所有股票的代码
     * @return
     */
    public List<String> getAllStockCodeFromBaseInfo();

    /**
     * 重新生成历史所有的股票基本面数据抓取的任务（从股票发行日，到指定时间每天（非周末）的抓取任务）
     * @param now
     * @return
     */
    public ResultVo regenerateAllStockMentalDataFromListTime2Now(Date now);

    /**
     * 生成指定时间的所有未退市股票的基本面数据抓取任务
     * @param date
     * @return
     */
    public ResultVo createDailyStockMentalInfoCrawlTask(Date date);
}
