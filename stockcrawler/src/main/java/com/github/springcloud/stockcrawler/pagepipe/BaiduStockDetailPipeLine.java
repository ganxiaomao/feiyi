package com.github.springcloud.stockcrawler.pagepipe;

import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.github.springcloud.stockcrawler.common.EntityConvertUtils;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.pageenity.BaiduStockDetail;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 14/02/2018.
 */
public class BaiduStockDetailPipeLine implements Pipeline<BaiduStockDetail> {
    Logger logger = LoggerFactory.getLogger(StockStarComPipeline.class);

    @Resource(name = "stockCrawlerServiceImpl")
    private StockCrawlerService stockCrawlerService;

    public static List<HttpRequest> requests = new ArrayList<>();

    @Override
    public void process(BaiduStockDetail detail) {
        logger.info("进入到 BaiduStockDetailPipeLine");
        Date now = new Date();
        StockDetailDayRecordEntity entity = EntityConvertUtils.convertBaiduStockDetail2StockDetailDayRecord(detail);
        entity.setId(new UUID().toString());
        entity.setCreateTime(now);
        ResultVo vo = stockCrawlerService.baiduStockDetailSave(entity);
        logger.info("存储结果："+vo.isSuccess()+",id="+entity.getId());
    }
}
