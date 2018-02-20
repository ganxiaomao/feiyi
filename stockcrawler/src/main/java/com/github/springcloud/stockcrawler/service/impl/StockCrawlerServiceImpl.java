package com.github.springcloud.stockcrawler.service.impl;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.github.pagehelper.PageRowBounds;
import com.github.springcloud.stockcrawler.dbdao.StockBaseInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDetailDayRecordDao;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.collect.Lists;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ResultVo stockBaseInfoBatchSave(List<StockBaseInfoEntity> entities) {
        if(entities != null){
            for(StockBaseInfoEntity entity : entities)
                //首先从数据表中查找是否存在同样stockCode的数据
                //StockBaseInfoEntity tmp1 = null;
                //tmp1 = stockBaseInfoDao.selectOneByStockCode(entity.getStockCode());
                stockBaseInfoDao.insert(entity);
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
}
