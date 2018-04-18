package com.github.springcloud.stockcrawler.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.github.pagehelper.PageRowBounds;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.dbdao.StockBaseInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDailyMentalInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDetailDayRecordDao;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
@Service("stockCrawlerServiceImpl")
public class StockCrawlerServiceImpl implements StockCrawlerService{
    Logger logger = LoggerFactory.getLogger(StockCrawlerServiceImpl.class);

    @Autowired
    private StockBaseInfoDao stockBaseInfoDao;

    @Autowired
    private StockDetailDayRecordDao stockDetailDayRecordDao;

    @Autowired
    private StockDailyMentalInfoDao stockDailyMentalInfoDao;

    @Resource(name="springPipelineFactory")
    private PipelineFactory springPipelineFactory;


    @Override
    public ResultVo crawlStockBaseInfo() {
        HttpRequest request = new HttpGetRequest("http://quote.stockstar.com/stock/stock_index.htm");
        //查看网站的编码，填充在这里
        request.setCharset("gb2312");
        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                //如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
                .classpath("com.github.springcloud.stockcrawler")
                .start(request)
                .thread(1)
                .interval(2000)
                .loop(false)
                .mobile(false)
                .start();
        return new ResultVo(true,null,"抓取完毕");
    }

    @Override
    public ResultVo crawlBaiduStockDetail() {
        //从数据表里找数据
        List<StockBaseInfoEntity> entities = stockBaseInfoDao.selectAll();
        //组织成url列表
        int size = entities.size();
        String[] urls = new String[size];
        int index = 0;
        for(StockBaseInfoEntity entity : entities){
            String url = "";
            if(entity.getStockType() == 1)
                url = "https://gupiao.baidu.com/stock/sh"+entity.getStockCode()+".html";
            else
                url = "https://gupiao.baidu.com/stock/sz"+entity.getStockCode()+".html";
            urls[index] = url;
            index++;
        }
        logger.info("本次股票详情地址共"+urls.length+"条");
        GeccoEngine.create()
                .pipelineFactory(springPipelineFactory)
                //如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
                .classpath("com.github.springcloud.stockcrawler")
                .start("")
                .thread(1)
                .interval(2000)
                .loop(false)
                .mobile(false)
                .start();
        return new ResultVo(true,null,"抓取完毕");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultVo stockBaseInfoBatchSave(List<StockBaseInfoEntity> entities) {
        if(entities != null){
            for(StockBaseInfoEntity entity : entities){
                //首先从数据表中查找是否存在同样stockCode的数据
                StockBaseInfoEntity tmp1 = null;
                tmp1 = stockBaseInfoDao.selectOneByStockCode(entity.getStockCode());
                if(tmp1 == null)//存在则不插入
                    stockBaseInfoDao.insert(entity);
            }
        }
        return new ResultVo(true,null,"保存成功");
    }

    @Override
    public ResultVo baiduStockDetailSave(StockDetailDayRecordEntity entity) {
        ResultVo vo = new ResultVo();
        try{
            stockDetailDayRecordDao.insert(entity);
            vo.success(entity.getId(),"保存成功");
        }
        catch(Exception e)
        {
            vo.fail(null,"保存失败");
            logger.info("保存异常：",e);
        }

        return vo;
    }

    @Override
    public void quartzTest() {
        logger.info("quartz测试数据："+Math.random()+"");
    }

    @Override
    public ResultVo crawlDailyStockInfoFromLixingren(String stockCode, String date) {
        String url = "https://www.lixinger.com/api/open/a/stock/fundamental-info";
        JSONObject jo = new JSONObject();
        jo.put("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        jo.put("date",date);
        JSONArray ja3 = new JSONArray();
        ja3.add("pe_ttm");
        ja3.add("pb");
        ja3.add("market_value");
        ja3.add("dividend_r");
        jo.put("metrics",ja3);
        JSONArray ja4 = new JSONArray();
        ja4.add(stockCode);
        jo.put("stockCodes",ja4);

        logger.info("请求理杏仁股票基本面信息接口，请求参数为："+jo.toJSONString());
        try{
            String responseStr = HttpUtils.httpPostBody(url,jo.toJSONString());
            logger.info("理杏仁响应数据为："+responseStr);
            Gson gson = new Gson();
            List<StockDailyMentalInfoEntity> entities = JSONObject.parseArray(responseStr,StockDailyMentalInfoEntity.class);
            if(entities != null){
                Date createTime = new Date();
                logger.info("接收的数据为："+gson.toJson(entities));
                for(StockDailyMentalInfoEntity entity:entities){
                    entity.setId(new UUID().toString());
                    entity.setCreateTime(createTime);
                    //理杏仁返回的是格林威治时间，gson强转后，时间会比实际时间大了8个小时，所以要减去8小时转换为本地时间
                    entity.setDate(DateUtils.plus(entity.getDate(),-8,"hour"));
                    stockDailyMentalInfoDao.insert(entity);
                }
            }
        }
        catch(Exception e){
            logger.info("error:",e);
        }

        return new ResultVo(true,null,"获取成功");
    }

    public List<StockBaseInfoEntity> getAllStockBaseInfo(){
        List<StockBaseInfoEntity> entities = stockBaseInfoDao.selectAll();
        if(entities == null)
            entities = Lists.newArrayList();
        return entities;
    }

    public String arrangeLixingerStockMentalInfoParam(String date){
        JSONObject jo = new JSONObject();
        jo.put("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        jo.put("date",date);
        JSONArray ja3 = new JSONArray();
        ja3.add("pe_ttm");
        ja3.add("pb");
        ja3.add("market_value");
        ja3.add("dividend_r");
        jo.put("metrics",ja3);
        JSONArray ja4 = new JSONArray();
        List<StockBaseInfoEntity> entities = getAllStockBaseInfo();
        if(!entities.isEmpty()){
            for(StockBaseInfoEntity entity : entities){
                ja4.add(entity.getStockCode());
            }
            jo.put("stockCodes",ja4);
        }
        else
            return "";
        return jo.toJSONString();

    }
}
