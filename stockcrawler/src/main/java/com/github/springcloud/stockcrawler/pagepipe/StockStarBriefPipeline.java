package com.github.springcloud.stockcrawler.pagepipe;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.pageenity.StockStarBriefPage;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by ganzhen on 18/04/2018.
 */
public class StockStarBriefPipeline implements Pipeline<StockStarBriefPage> {
    Logger logger = LoggerFactory.getLogger(StockStarBriefPipeline.class);

    @Resource(name = "stockCrawlerServiceImpl")
    private StockCrawlerService stockCrawlerService;

    @Override
    public void process(StockStarBriefPage stockStarBriefPage) {
        logger.info("进入到StockStarBriefPipeline");
        String listedTimeStr = stockStarBriefPage.getListedTime();
        String stockCode = stockStarBriefPage.getStockCode();
        //根据stockCode找到StockBaseInfo
        StockBaseInfoEntity entity = stockCrawlerService.findOneStockBaseInfoByStockCode(stockCode);
        if(entity != null){
            //将字符串的上市时间改为date类型
            try {
                Date listedTime = DateUtils.convertString2Date(listedTimeStr,"yyyy-MM-dd");
                entity.setListedTime(listedTime);
                stockCrawlerService.updateStrockBaseInfo(entity);
            } catch (Exception e) {
                logger.info("error",e);
            }

        }

    }
}