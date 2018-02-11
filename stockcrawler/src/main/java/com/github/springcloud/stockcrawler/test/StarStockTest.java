package com.github.springcloud.stockcrawler.test;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;

/**
 * Created by ganzhen on 11/02/2018.
 */
public class StarStockTest {
    public static void main(String[] args){
        HttpRequest request = new HttpGetRequest("http://quote.stockstar.com/stock/stock_index.htm");
        request.setCharset("gb2312");
        GeccoEngine.create()
                .classpath("com.github.springcloud.stockcrawler")
                .start(request)
                .thread(1)
                .interval(2000)
                .loop(false)
                .mobile(false)
                .run();
    }
}
