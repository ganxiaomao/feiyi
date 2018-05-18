package com.github.springcloud.stockcrawler.thread;

import com.alibaba.fastjson.JSONObject;
import com.eaio.uuid.UUID;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by ganzhen on 2018/5/4.
 */
@Component
@Scope("prototype")
public class StockMentalInfoProducer implements Callable<List<StockDailyMentalInfoEntity>>{
    private static final Logger logger = LoggerFactory.getLogger(StockMentalInfoProducer.class);

    private String requestStr;

    public StockMentalInfoProducer(String requestStr){
        this.requestStr = requestStr;
    }


    @Override
    public List<StockDailyMentalInfoEntity> call() throws Exception {
        List<StockDailyMentalInfoEntity> entities = Lists.newArrayList();
        String url = "https://www.lixinger.com/api/open/a/stock/fundamental-info";
        logger.info("请求理杏仁股票基本面信息接口，请求参数为："+requestStr);
        try{
            String responseStr = HttpUtils.httpPostBody(url,requestStr);
            logger.info("理杏仁响应数据为："+responseStr);
            Gson gson = new Gson();
            List<StockDailyMentalInfoEntity> tmps = JSONObject.parseArray(responseStr,StockDailyMentalInfoEntity.class);
            if(tmps != null){
                Date createTime = new Date();
                logger.info("接收的数据为："+gson.toJson(tmps));
                for(StockDailyMentalInfoEntity entity:tmps){
                    entity.setId(new UUID().toString());
                    entity.setCreateTime(createTime);
                    //理杏仁返回的是格林威治时间，gson强转后，时间会比实际时间大了8个小时，所以要减去8小时转换为本地时间
                    entity.setDate(DateUtils.plus(entity.getDate(),-8,"hour"));
                    entity.setCrawled(1);//已抓取状态设置为1
                    entities.add(entity);
                }
            }
        }
        catch(Exception e){
            logger.info("error:",e);
        }
        //Gson gson = new Gson();
        //if(entities != null && !entities.isEmpty())
            //res = gson.toJson(entities);
        return entities;
    }
}
