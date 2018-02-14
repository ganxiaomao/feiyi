package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockBaseInfoDao extends MyMapper<StockBaseInfoEntity> {

    public StockBaseInfoEntity selectOneByStockCode(String stockCode);

    public List<StockBaseInfoEntity> selectDatasByStockName(String stockName);

}
