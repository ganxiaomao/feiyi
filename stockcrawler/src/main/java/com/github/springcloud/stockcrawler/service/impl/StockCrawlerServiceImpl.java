package com.github.springcloud.stockcrawler.service.impl;

import com.github.springcloud.stockcrawler.dbdao.StockBaseInfoDao;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ganzhen on 11/02/2018.
 */
@Service
public class StockCrawlerServiceImpl implements StockCrawlerService{
    Logger logger = LoggerFactory.getLogger(StockCrawlerServiceImpl.class);

    @Autowired
    private StockBaseInfoDao stockBaseInfoDao;

    @Override
    public ResultVo crawlStockBaseInfo() {
        return null;
    }
}
