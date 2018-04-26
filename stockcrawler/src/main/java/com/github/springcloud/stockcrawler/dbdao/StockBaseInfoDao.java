package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.MyMapper
 */
public interface StockBaseInfoDao extends SuperMapperPlus<StockBaseInfoEntity> {

    public StockBaseInfoEntity selectOneByStockCode(String stockCode);

    public List<StockBaseInfoEntity> selectDatasByStockName(String stockName);

}
