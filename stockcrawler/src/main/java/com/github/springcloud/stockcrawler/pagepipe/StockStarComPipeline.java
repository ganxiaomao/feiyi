package com.github.springcloud.stockcrawler.pagepipe;

import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HrefBean;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.pageenity.StockStarCodeAndName;
import com.github.springcloud.stockcrawler.pageenity.StockStarComStockCodesPageEntity;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
//@PipelineName("stockStarComStockCodesPipeline")
//@Service
public class StockStarComPipeline implements Pipeline<StockStarComStockCodesPageEntity>{
    Logger logger = LoggerFactory.getLogger(StockStarComPipeline.class);

    @Resource(name = "stockCrawlerServiceImpl")
    private StockCrawlerService stockCrawlerService;

    public static List<HttpRequest> cateRequests = new ArrayList<>();

    @Override
    public void process(StockStarComStockCodesPageEntity stockStarComStockCodesPageEntity) {
        logger.info("进入到stockStarComStockCodesPipeline");
        Date now = new Date();
        List<StockStarCodeAndName> sscns = stockStarComStockCodesPageEntity.getSscns();
        List<StockBaseInfoEntity> entities = new ArrayList<>();
        for(StockStarCodeAndName sscn : sscns){
            List<HrefBean> hbs = sscn.getUrls();
            String stockName = "";
            Integer stockType = 1;
            if(sscn.getStockCodeUrl().contains("sz_"))
                stockType = 2;

            for(HrefBean hb : hbs){
                stockName = hb.getTitle();
            }
            stockName = stockName.replaceAll(" ","");

            StockBaseInfoEntity entity = new StockBaseInfoEntity();
            entity.setCreateTime(now);
            entity.setStockCode(sscn.getStockCode());
            entity.setStockName(stockName);
            entity.setStockType(stockType);
            entity.setId(new UUID().toString());
            entities.add(entity);

            logger.info(stockType+"股票:"+sscn.getStockCode()+"="+stockName);
        }
        if(stockCrawlerService != null){
            ResultVo vo = stockCrawlerService.stockBaseInfoBatchSave(entities);
            logger.info("保存结果："+vo.getMsg()+"，抓取的数据数量："+sscns.size());
        }
        else
            logger.info("stockCrawlerService为null");
    }
}
