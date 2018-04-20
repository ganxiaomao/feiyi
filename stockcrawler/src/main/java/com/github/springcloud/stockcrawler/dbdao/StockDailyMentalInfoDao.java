package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.MyMapper;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by ganzhen on 18/04/2018.
 */
public interface StockDailyMentalInfoDao  extends MyMapper<StockDailyMentalInfoEntity> {

    /**
     * 根据stockCode，按照date由早到晚的顺序排列，获取数据
     * @param stockCode
     * @return
     */
    public List<StockDailyMentalInfoEntity> findAllByStockCode(String stockCode);

    /**
     * 根据stockCode和数据时间date，获取所有≤date的数据
     * @param stockCode
     * @param date
     * @return
     */
    public List<StockDailyMentalInfoEntity> findAllByStockCodeAndDate(String stockCode, String date);

    /**
     * 根据stockCode更新crawled字段
     * @param params 包含stockCode、crawled
     * @return
     */
    public int updateCrawledByStockCode(Map<String,Object> params);
}
