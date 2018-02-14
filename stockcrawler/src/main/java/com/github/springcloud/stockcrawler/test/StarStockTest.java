package com.github.springcloud.stockcrawler.test;

import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;

/**
 * Created by ganzhen on 11/02/2018.
 */
public class StarStockTest {
    public static void main(String[] args){
        HttpRequest request = new HttpGetRequest("https://gupiao.baidu.com/stock/sh601766.html");
        //查看网站的编码，填充在这里
        //request.setCharset("gb2312");
        GeccoEngine.create()
                //如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
                .classpath("com.github.springcloud.stockcrawler")
                .start("https://gupiao.baidu.com/stock/sh601766.html","https://gupiao.baidu.com/stock/sh601989.html")
                .thread(1)
                .interval(2000)
                .loop(false)
                .mobile(false)
                .run();
    }
}
