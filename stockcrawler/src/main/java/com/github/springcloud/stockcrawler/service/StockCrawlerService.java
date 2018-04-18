package com.github.springcloud.stockcrawler.service;

import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.vo.ResultVo;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockCrawlerService {

    public ResultVo crawlStockBaseInfo();

    public ResultVo crawlBaiduStockDetail();

    public ResultVo stockBaseInfoBatchSave(List<StockBaseInfoEntity> entities);

    public ResultVo baiduStockDetailSave(StockDetailDayRecordEntity entity);

    public void quartzTest();

    public ResultVo crawlDailyStockInfoFromLixingren(String stockCode,String date);

    public boolean updateStrockBaseInfo(StockBaseInfoEntity entity);

    public StockBaseInfoEntity findOneStockBaseInfoByStockCode(String stockCode);
}
