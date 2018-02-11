package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockBaseInfoDao extends Mapper<StockBaseInfoEntity> {

    public StockBaseInfoEntity selectOneByStockCode(String stockCode);

    public List<StockBaseInfoEntity> selectDatasByStockName(String stockName);

}
