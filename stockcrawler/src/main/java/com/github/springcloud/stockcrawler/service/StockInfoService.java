package com.github.springcloud.stockcrawler.service;

import com.github.pagehelper.PageInfo;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;

/**
 * Created by ganzhen on 14/02/2018.
 */
public interface StockInfoService {

    public PageInfo<StockDetailDayRecordEntity> queryAllStockDetailDayRecordPage(Integer pageNo, Integer pageSize);
}
