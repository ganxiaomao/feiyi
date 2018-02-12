package com.github.springcloud.stockcrawler.pagepipe;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean;

/**
 * Created by ganzhen on 12/02/2018.
 */
public class ConsolePipeline implements Pipeline<SpiderBean> {

    @Override
    public void process(SpiderBean spiderBean) {
        System.out.println("console print:"+JSON.toJSON(spiderBean));
    }
}
