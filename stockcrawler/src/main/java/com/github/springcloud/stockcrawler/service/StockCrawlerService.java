package com.github.springcloud.stockcrawler.service;

import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.vo.ResultVo;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockCrawlerService {

    public ResultVo crawlStockBaseInfo();

    public ResultVo stockBaseInfoBatchSave(List<StockBaseInfoEntity> entities);
}
