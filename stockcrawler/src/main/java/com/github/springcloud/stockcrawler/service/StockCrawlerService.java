package com.github.springcloud.stockcrawler.service;

import com.github.springcloud.stockcrawler.vo.ResultVo;

/**
 * Created by ganzhen on 11/02/2018.
 */
public interface StockCrawlerService {

    public ResultVo crawlStockBaseInfo();
}
