package com.github.springcloud.stockcrawler.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eaio.uuid.UUID;
import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.constant.StockDailyConstant;
import com.github.springcloud.stockcrawler.dbdao.FundingBaseInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockBaseInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDailyMentalInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDetailDayRecordDao;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyCrawlTaskEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDetailDayRecordEntity;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
@Service("stockCrawlerServiceImpl")
public class StockCrawlerServiceImpl  extends ServiceImpl<StockBaseInfoDao, StockBaseInfoEntity> implements StockCrawlerService{
    Logger logger = LoggerFactory.getLogger(StockCrawlerServiceImpl.class);

    @Resource
    private StockBaseInfoDao stockBaseInfoDao;

    @Autowired
    private StockDetailDayRecordDao stockDetailDayRecordDao;

    @Resource
    private StockDailyMentalInfoDao stockDailyMentalInfoDao;

    @Resource(name="springPipelineFactory")
    private PipelineFactory springPipelineFactory;

    @Autowired
    private FundingBaseInfoDao fundingBaseInfoDao;

    @Autowired
    private StockMentalInfoService stockMentalInfoServiceImpl;


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
        List<StockBaseInfoEntity> entities = getAllStockBaseInfo();
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
        boolean success = false;
        String msg = "每日股票基本面信息获取失败";
        String requestStr = arrangeLixingerStockMentalInfoParam(date);

        List<StockDailyMentalInfoEntity> entities = stockMentalInfoServiceImpl.fetchStockMentalInfoFromLixingren(requestStr);
        try{
            //entities不为null，则批量插入数据表
            stockDailyMentalInfoDao.batchInsertList(entities);
            success = true;
            msg = "每日股票基本面信息获取成功";
        }
        catch(Exception e){
            logger.info("error",e);
            msg = e.getMessage();
        }


        return new ResultVo(success,null,msg);
    }


    public boolean batchUpdateStockDailyMentalInfo(List<StockDailyMentalInfoEntity> entities){

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean updateStrockBaseInfo(StockBaseInfoEntity entity) {
        if(entity != null){
            stockBaseInfoDao.updateById(entity);//.updateByPrimaryKey(entity);
            return true;
        }
        return false;
    }

    @Override
    public StockBaseInfoEntity findOneStockBaseInfoByStockCode(String stockCode) {
        if(!Strings.isNullOrEmpty(stockCode)){
            return stockBaseInfoDao.selectOneByStockCode(stockCode);
        }
        return null;
    }

    @Override
    public ResultVo crawlStockStarBriefInfo(List<String> stockCodes) {
        if(stockCodes != null && !stockCodes.isEmpty()){
            List<HttpRequest> hgrs = Lists.newArrayList();
            int count = 0;
            for(String stockCode : stockCodes){
                String url = "http://stock.quote.stockstar.com/corp/brief_"+stockCode+".shtml";
                HttpRequest request = new HttpGetRequest(url);
                //查看网站的编码，填充在这里
                request.setCharset("gb2312");
                hgrs.add(request);
            }
            GeccoEngine.create()
                    .pipelineFactory(springPipelineFactory)
                    //如果pipeline和htmlbean不在同一个包，classpath就要设置到他们的共同父包
                    .classpath("com.github.springcloud.stockcrawler")
                    .start(hgrs)
                    .thread(3)
                    .interval(2000)
                    .loop(false)
                    .mobile(false)
                    .start();
        }
        return new ResultVo(true,null,"抓取完毕");
    }

    @Override
    public List<String> getAllStockCodeFromBaseInfo() {
        List<StockBaseInfoEntity> entities = getAllStockBaseInfo();
        List<String> stockCodes = Lists.newArrayList();
        if(entities != null){
            for(StockBaseInfoEntity entity : entities){
                stockCodes.add(entity.getStockCode());
            }
        }
        return stockCodes;
    }

    @Override
    public ResultVo regenerateAllStockMentalDataFromListTime2Now(Date now) {
        //获取所有未退市的stockBaseInfo的数据
        List<StockBaseInfoEntity> entities = getAllStockBaseInfo();

        if(entities != null){
            for(StockBaseInfoEntity entity : entities){
                List<StockDailyCrawlTaskEntity> stockDailyCrawlTaskEntities = Lists.newArrayList();
                Date listedTime = entity.getListedTime();
                List<Date> everyDays = DateUtils.getEveryDayFromThen2NowByMaxYears(listedTime,new Date(),10);
                for(Date everyDay : everyDays){
                    if(DateUtils.dayOfWeek(everyDay)<6){//不是周末，股票信息才会有数据
                        StockDailyCrawlTaskEntity stockDailyCrawlTaskEntity = new StockDailyCrawlTaskEntity();
                        stockDailyCrawlTaskEntity.setId(new UUID().toString());
                        stockDailyCrawlTaskEntity.setCrawlDate(everyDay);
                        stockDailyCrawlTaskEntity.setTaskStatus(StockDailyConstant.stock_daily_crawl_task_status_undo);
                        stockDailyCrawlTaskEntity.setTaskType(StockDailyConstant.stock_daily_crawl_task_type_mental);
                        stockDailyCrawlTaskEntities.add(stockDailyCrawlTaskEntity);
                    }
                }
                //保存
                logger.info("重新生成stockCode="+entity.getStockCode()+"的从上市到当前时间的基本面信息抓取任务共"+stockDailyCrawlTaskEntities.size()+"条");
                stockMentalInfoServiceImpl.batchInsertStockDailyCrawlTask(stockDailyCrawlTaskEntities);
            }
        }
        return new ResultVo(true,null,"重新生成完毕");
    }

    @Override
    public ResultVo createDailyStockMentalInfoCrawlTask(Date date) {
        logger.info("开始生成每日股票基本面数据抓取的任务");
        List<StockDailyMentalInfoEntity> dailyMentalInfoEntities = Lists.newArrayList();
        //获取所有未退市的stockBaseInfo的数据
        List<StockBaseInfoEntity> entities = getAllStockBaseInfo();
        //按日期查询是否已存在的生成的任务
        List<StockDailyMentalInfoEntity> exitedDailyEntities = stockMentalInfoServiceImpl.getDatasByDate(date);
        try{
            //判断是否为周末
            if(DateUtils.dayOfWeek(date) < 6){
                for(StockBaseInfoEntity entity : entities){
                    StockDailyMentalInfoEntity dailyMentalInfoEntity = new StockDailyMentalInfoEntity();
                    dailyMentalInfoEntity.setId(new UUID().toString());
                    dailyMentalInfoEntity.setCreateTime(date);
                    dailyMentalInfoEntity.setCrawled(0);

                    //判断是否已存在
                    if(exitedDailyEntities != null && !exitedDailyEntities.isEmpty()){
                        if(exitedDailyEntities.contains(dailyMentalInfoEntity))
                            continue;
                    }
                    //
                    dailyMentalInfoEntities.add(dailyMentalInfoEntity);
                }
            }
            //批量保存
            if(!dailyMentalInfoEntities.isEmpty()){
                logger.info("每日股票基本面数据抓取的任务生成"+dailyMentalInfoEntities.size()+"条数据");
                batchInsertStockDailyMentalInfo(dailyMentalInfoEntities);
            }
        }
        catch(Exception e){
            logger.info("error",e);
        }


        logger.info("结束生成每日股票基本面数据抓取的任务");
        return null;
    }

    /**
     * 获取所有未退市的股票
     * @return
     */
    public List<StockBaseInfoEntity> getAllStockBaseInfo(){
        //找到所有未退市的股票
//        Example example = new Example(StockBaseInfoEntity.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andCondition("delist=",0);
        Wrapper wrapper = new EntityWrapper();
        //wrapper.eq("delist",0);
        wrapper.where("delist={0}",0).last("LIMIT 10");
        List<StockBaseInfoEntity> entities = baseMapper.selectList(wrapper);//.selectByExample(example);
        if(entities == null)
            entities = Lists.newArrayList();
        return entities;
    }

    /**
     * 整合理杏仁股票基本面数据接口所需的参数
     * @param date
     * @return
     */
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
        //不添加stockCodes，则会返回所有未退市的股票基本面数据
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

    /**
     * 批量保存股票日常基本面数据
     * @param entities
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean batchInsertStockDailyMentalInfo(List<StockDailyMentalInfoEntity> entities){
//        if(entities != null){
//            for(StockDailyMentalInfoEntity entity : entities){
//                stockDailyMentalInfoDao.insert(entity);
//            }
//        }
        stockDailyMentalInfoDao.batchInsertList(entities);//.insertList(entities);
        //StockBaseInfoEntity entity = stockBaseInfoDao.selectOneByStockCode("000625");//.findAllByStockCode("000625");
        return true;
    }
}
