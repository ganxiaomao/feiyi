package com.github.springcloud.stockcrawler.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.springcloud.stockcrawler.dbdao.StockDetailDayRecordDao;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.service.StockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ganzhen on 14/02/2018.
 */
@Service("stockInfoServiceImpl")
public class StockInfoServiceImpl implements StockInfoService {

    @Autowired
    private StockDetailDayRecordDao stockDetailDayRecordDao;


    @Override
    public PageInfo<StockDetailDayRecordEntity> queryAllStockDetailDayRecordPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<StockDetailDayRecordEntity> list = null;//stockDetailDayRecordDao.selectAll();

        return new PageInfo<>(list);//这里就会根据配置文件中的"params: count=countSql"，来自动获取总个数
    }


}
