package com.github.springcloud.stockcrawler.controller;

import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by ganzhen on 11/02/2018.
 */
@RestController
@RequestMapping("crawler")
public class CrawlerController {


    @Autowired
    private StockCrawlerService stockCrawlerService;

    @Autowired
    private StockMentalInfoService stockMentalInfoServiceImpl;

    @RequestMapping(value="crawlStockStar",method= RequestMethod.GET)
    public ResponseEntity<?> crawlStockStar(){
        stockCrawlerService.crawlStockBaseInfo();
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value="crawlBaiduStockDetail",method = RequestMethod.GET)
    public ResponseEntity<?> crawlBaiduStockDetail(){
        stockCrawlerService.crawlBaiduStockDetail();
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value="crawlStockMentalInfo",method = RequestMethod.GET)
    public ResponseEntity<?> crawlStockMentalInfo(@RequestParam(value="stockCode") String stockCode,@RequestParam(value="date") String date){
        return ResponseEntity.ok(stockCrawlerService.crawlDailyStockInfoFromLixingren(stockCode,date));
    }

    @RequestMapping(value = "crawlStockStarBriefInfo",method =  RequestMethod.GET)
    public ResponseEntity<?> crawlStockStarBriefInfo(@RequestParam(value="stockCode") String stockCode){
        return ResponseEntity.ok(stockCrawlerService.crawlStockStarBriefInfo(stockCrawlerService.getAllStockCodeFromBaseInfo()));
    }

    @RequestMapping(value = "regenerateAllStockMentalDataFromListTime2Now",method = RequestMethod.GET)
    public ResponseEntity<?> regenerateAllStockMentalDataFromListTime2Now(@RequestParam(value="date") String date){
        try {
            Date temp = DateUtils.convertString2Date(date,"yyyy-MM-dd");
            stockCrawlerService.regenerateAllStockMentalDataFromListTime2Now(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(true);
    }

    /**
     * 修正stockbaseinfo的数据，有些股票新增，有些退市，需要定期修正一i啊
     * @return
     */
    @RequestMapping(value = "fixAllStockBaseInfo", method = RequestMethod.GET)
    public ResponseEntity<?> fixAllStockBaseInfo(){
        ResultVo rv = stockCrawlerService.fixAllStockBaseInfo();
        return ResponseEntity.ok(rv);
    }

    @RequestMapping(value = "exeCrawlStockMentalInfoTask",method = RequestMethod.GET)
    public ResponseEntity exeCrawlStockMentalInfoTask(){
        ResultVo rv = stockMentalInfoServiceImpl.exeCrawlStockMentalInfoTask();
        return ResponseEntity.ok(rv);
    }
}
