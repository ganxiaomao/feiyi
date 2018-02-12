package com.github.springcloud.stockcrawler.controller;

import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ganzhen on 11/02/2018.
 */
@RestController
@RequestMapping("crawler")
public class CrawlerController {


    @Autowired
    private StockCrawlerService stockCrawlerService;

    @RequestMapping(value="crwalStockStar",method= RequestMethod.GET)
    public ResponseEntity<?> crwalStockStar(){
        stockCrawlerService.crawlStockBaseInfo();
        return ResponseEntity.ok(true);
    }
}
