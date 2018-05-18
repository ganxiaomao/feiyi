package com.github.springcloud.stockcrawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eaio.uuid.UUID;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.common.BaseClassConvertUtils;
import com.github.springcloud.stockcrawler.common.LixingerUtils;
import com.github.springcloud.stockcrawler.common.MathUtils;
import com.github.springcloud.stockcrawler.constant.StockDailyConstant;
import com.github.springcloud.stockcrawler.dbdao.StockBaseInfoDao;
import com.github.springcloud.stockcrawler.dbdao.StockDailyCrawlTaskDao;
import com.github.springcloud.stockcrawler.dbdao.StockDailyMentalInfoDao;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyCrawlTaskEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.service.RedisServer;
import com.github.springcloud.stockcrawler.service.StockCrawlerService;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import com.github.springcloud.stockcrawler.thread.StockMentalInfoProducer;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by ganzhen on 2018/4/28.
 */
@Service("stockMentalInfoServiceImpl")
public class StockMentalInfoServiceImpl extends ServiceImpl<StockDailyMentalInfoDao,StockDailyMentalInfoEntity> implements StockMentalInfoService {
    Logger logger = LoggerFactory.getLogger(StockMentalInfoServiceImpl.class);

    @Resource
    private StockDailyCrawlTaskDao stockDailyCrawlTaskDao;

    @Resource
    private StockBaseInfoDao stockBaseInfoDao;

    @Autowired
    private StockCrawlerService stockCrawlerServiceImpl;

    @Autowired
    private RedisServer redisServerImpl;

    @Autowired
    private ApplicationContext context;

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    @Override
    public List<StockDailyMentalInfoEntity> getDatasByDate(Date date) {
        List<StockDailyMentalInfoEntity> entities = Lists.newArrayList();
        try{
            String dateStr = DateUtils.convertDate2String(date, "yyyy-MM-dd");
            Wrapper<StockDailyMentalInfoEntity> wrapper = new EntityWrapper<StockDailyMentalInfoEntity>();
            wrapper.where("CAST(create_time as date)",dateStr);
            entities = baseMapper.selectList(wrapper);
        }
        catch(Exception e){
            logger.info("error",e);
        }

        return entities;
    }

    @Override
    public ResultVo crawlStockDailyMentalInfoFromLixinger(Date date) {
        long startCrawl = System.currentTimeMillis();
        String dateStr = DateUtils.convertDate2String(date,"yyyy-MM-dd");
        boolean success = true;
        Object obj = null;
        String msg = dateStr+"日，股票基本面数据获取成功";
        String requestJson = LixingerUtils.arrangeLixingerStockMentalInfoParam(dateStr,null);



        List<StockDailyMentalInfoEntity> entities = fetchStockMentalInfoFromLixingren(requestJson);
        //批量保存
        try{
            Date mentalDate = null;
            if(entities != null && !entities.isEmpty()){
                mentalDate = entities.get(0).getDate();
            }
            //先根据日期查找符合的基本面信息记录，如果有的话，就不再生成记录
            List<StockDailyMentalInfoEntity> tmps = Lists.newArrayList();
            long startRedis = System.currentTimeMillis();
            for(StockDailyMentalInfoEntity entity : entities){
                String redis_key = StockDailyConstant.redis_key_prefix_stock_daily_mental_info+entity.getStockCode()+DateUtils.convertDate2String(entity.getDate(),null);
                String redis_value = redisServerImpl.get(redis_key);
                if(Strings.isNullOrEmpty(redis_value)){
                    if(entity.getPe_ttm() != null || entity.getPb() != null){
                        tmps.add(entity);
                        redis_value = "has";
                        redisServerImpl.set(redis_key,redis_value);
                    }

                }
//                int existNum = findCountByDateAndStockCode(entity.getDate(),entity.getStockCode());
//                if(existNum == 0 ){
//                    if(entity.getPe_ttm() != null || entity.getPb() != null)
//                        tmps.add(entity);
//                }
            }
            long endRedis = System.currentTimeMillis();
            logger.info("执行一次redis去重，耗时"+(endRedis-startRedis)+"毫秒");
            obj = tmps;
            logger.info(dateStr+"日，抓取到数据"+entities.size()+"条，去除已存在的，总共"+tmps.size()+"条数据。");
        }
        catch(Exception e){
            success = false;
            msg = dateStr+"日，股票基本面数据获取失败，失败原因："+e.getMessage();
            logger.info("error:",e);
        }
        long endCrawl = System.currentTimeMillis();
        logger.info("执行一次股票基本面获取+去重，耗时"+(endCrawl-startCrawl)+"毫秒");
        return new ResultVo(success,obj,msg);
    }

    /**
     * 从理杏仁网站获取股票基本面信息
     * @param requestStr
     * @return 返回股票基本面信息表对应的实体类数据
     */
    @Override
    public List<StockDailyMentalInfoEntity> fetchStockMentalInfoFromLixingren(String requestStr){
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
        return entities;
    }

    @Override
    public ResultVo createStockDailyCrawlTask(Date date, int taskType) {
        String msg = "生成股票日抓取任务失败";
        boolean success = false;
        if(date != null && taskType >= 0){
            StockDailyCrawlTaskEntity entity = new StockDailyCrawlTaskEntity();
            entity.setId(new UUID().toString());
            entity.setTaskType(taskType);
            entity.setCrawlDate(date);
            entity.setTaskStatus(StockDailyConstant.stock_daily_crawl_task_status_undo);

            stockDailyCrawlTaskDao.insert(entity);
            msg = "生成股票日抓取任务成功";
            success = true;
        }
        return new ResultVo(success,null,msg);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean batchInsertStockDailyCrawlTask(List<StockDailyCrawlTaskEntity> entities) {
        stockDailyCrawlTaskDao.batchInsertList(entities);
        return true;
    }

    @Override
    public ResultVo exeCrawlStockMentalInfoTask() {
        logger.info("开始执行抓取股票基本面信息任务");
        //首先查找crawltask表中未被抓取的数据
        int count = 0;
        while(true){
            count++;
            logger.info("抓取第"+count+"次的10条任务对应的信息");
            List<StockDailyCrawlTaskEntity> stockDailyCrawlTaskEntities = getCrawlTaskByTypeAndStatusAndLimit(StockDailyConstant.stock_daily_crawl_task_type_mental,StockDailyConstant.stock_daily_crawl_task_status_undo,3);
            List<String> success_ids = Lists.newArrayList();
            List<String> fail_ids = Lists.newArrayList();

            List<StockMentalInfoProducer> producers = Lists.newArrayList();
            for(StockDailyCrawlTaskEntity stockDailyCrawlTaskEntity : stockDailyCrawlTaskEntities){
//                ResultVo rv = crawlStockDailyMentalInfoFromLixinger(stockDailyCrawlTaskEntity.getCrawlDate());
//                if(rv.isSuccess()){
//                    //
//                    if(rv.getObj() != null){
//                        List<StockDailyMentalInfoEntity> added = (List<StockDailyMentalInfoEntity>) rv.getObj();
//                        if(!added.isEmpty())
//                            baseMapper.batchInsertList(added);
//                        success_ids.add(stockDailyCrawlTaskEntity.getId());
//                    }
//                }
//                else{
//                    //
//                    if(rv.getObj() != null){
//                        fail_ids.add(stockDailyCrawlTaskEntity.getId());
//                    }
//
//                }
                String dateStr = DateUtils.convertDate2String(stockDailyCrawlTaskEntity.getCrawlDate(),"yyyy-MM-dd");
                String requestJson = LixingerUtils.arrangeLixingerStockMentalInfoParam(dateStr,null);
                StockMentalInfoProducer producer = context.getBean(StockMentalInfoProducer.class,requestJson);
                producers.add(producer);

                success_ids.add(stockDailyCrawlTaskEntity.getId());
            }

            try {
                //long starMulti = System.currentTimeMillis();
                List<Future<List<StockDailyMentalInfoEntity>>> futures = executorService.invokeAll(producers);

                List<StockDailyMentalInfoEntity> tmps = Lists.newArrayList();
                for(Future<List<StockDailyMentalInfoEntity>> future : futures){
                    List<StockDailyMentalInfoEntity> lists = future.get(10000, TimeUnit.MICROSECONDS);
                    for(StockDailyMentalInfoEntity e :lists){
                        String redis_key = StockDailyConstant.redis_key_prefix_stock_daily_mental_info+e.getStockCode()+DateUtils.convertDate2String(e.getDate(),null);
                        String redis_value = redisServerImpl.get(redis_key);
                        if(Strings.isNullOrEmpty(redis_value)){
                            if(e.getPe_ttm() != null || e.getPb() != null){
                                tmps.add(e);
                                redis_value = "has";
                                redisServerImpl.set(redis_key,redis_value);
                            }

                        }
                    }
                    //tmps.addAll(future.get(10000, TimeUnit.MICROSECONDS));

                    logger.info("多线程获取的数据总共："+tmps.size()+"条");
                }


                /*List<StockDailyMentalInfoEntity> entities = Lists.newArrayList();
                if(!tmps.isEmpty()){
                    //去重
                    for (StockDailyMentalInfoEntity e : tmps){
                        String redis_key = StockDailyConstant.redis_key_prefix_stock_daily_mental_info+e.getStockCode()+DateUtils.convertDate2String(e.getDate(),null);
                        String redis_value = redisServerImpl.get(redis_key);
                        if(Strings.isNullOrEmpty(redis_value)){
                            if(e.getPe_ttm() != null || e.getPb() != null){
                                entities.add(e);
                                redis_value = "has";
                                redisServerImpl.set(redis_key,redis_value);
                            }

                        }
                    }
                }*/
                //long endMulti = System.currentTimeMillis();
                //logger.info("redis去重耗时："+(endMulti-starMulti));
                logger.info("总共待插入数据"+tmps.size()+"条");
                if(!tmps.isEmpty()){
                    baseMapper.batchInsertList(tmps);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //更新taskstatus为1，成功
            if(!success_ids.isEmpty()){
                Map<String,Object> params = Maps.newHashMap();
                params.put("taskStatus",1);
                params.put("list",success_ids);
                stockDailyCrawlTaskDao.updateTaskStatusByIds(params);
            }
            //更新taskstatus为-1，失败
            if(!fail_ids.isEmpty()){
                Map<String,Object> params = Maps.newHashMap();
                params.put("taskStatus",-1);
                params.put("list",fail_ids);
                stockDailyCrawlTaskDao.updateTaskStatusByIds(params);
            }

            if(stockDailyCrawlTaskEntities == null || stockDailyCrawlTaskEntities.isEmpty())
                break;
        }
        logger.info("结束执行抓取股票基本面信息任务");
        return new ResultVo(true,null,"抓取股票基本面信息任务执行完毕");
    }


    @Override
    public ResultVo computeDailyStockDegree() {
        //1 从stockbaseinfo表获取所有未退市的stockcode
        List<String> stockCodes = stockCrawlerServiceImpl.getAllStockCodeFromBaseInfo();
        //2 遍历每个stockCode，根据stockcode从stockmentalinfo表根据date的正序获取所有数据
        StockDailyMentalInfoEntity tmp = baseMapper.selectById("6c38bc0c-4bc8-11e8-8a70-9a5fd3589e5b");
        for(String stockCode : stockCodes){
            logger.info("开始计算stockCode="+stockCode+"股票温度");
            List<StockDailyMentalInfoEntity> entities = findDatasByStocCodeOrderByDateAsc(stockCode);
            //循环每个stockDailyMentalInfo信息，degreed=0的，计算其温度
            List<StockDailyMentalInfoEntity> degreedEntities = computeStockDailyMentalInfoDatasDegree(entities);
            if(!degreedEntities.isEmpty()){
                //批量更新
                batchUpdateStockDailyMentalInfo(degreedEntities);
                logger.info("总共更新股票温度"+degreedEntities.size()+"条数据");
            }

            logger.info("完成计算stockCode="+stockCode+"股票温度，更新数据"+degreedEntities.size()+"条");
        }
        return new ResultVo(true,null,"计算股票温度任务执行完毕");
    }

    @Override
    public ResultVo computeStockLatestDegreeByStockCode(String stockCode) {
        String msg = "计算成功";
        Object obj = null;
        boolean success = true;
        if(!Strings.isNullOrEmpty(stockCode)){
            List<StockDailyMentalInfoEntity> entities = findDatasByStocCodeOrderByDateAsc(stockCode);
            if(entities.size() == 0){
                success = false;
                msg = "找不到代码"+stockCode+"的股票数据，请确认代码是否正确。";
            }
            else{
                List<StockDailyMentalInfoEntity> degreeds = computeStockDailyMentalInfoDatasDegree(entities);
                if(!degreeds.isEmpty()){
                    int size = degreeds.size();
                    StockDailyMentalInfoEntity latest = degreeds.get(size-1);
                    obj = latest.getStockDegree().multiply(new BigDecimal(100));
                    msg += DateUtils.convertDate2String(latest.getDate(),"yyyy-MM-dd");
                }
                else{
                    int size = entities.size();
                    StockDailyMentalInfoEntity latest = entities.get(size-1);
                    obj = latest.getStockDegree();
                    msg += DateUtils.convertDate2String(latest.getDate(),"yyyy-MM-dd");
                }
            }
        }
        return new ResultVo(success,obj,msg);
    }

    @Override
    public ResultVo fetchStockMentalInfoByCodeAndDate(String stockCode, String date) {
        boolean success = false;
        Object obj = null;
        String msg = "抓取失败";
        if(!Strings.isNullOrEmpty(stockCode) && !Strings.isNullOrEmpty(date)){
            String requestJson = LixingerUtils.arrangeLixingerStockMentalInfoParam(date,Lists.newArrayList(stockCode));
            List<StockDailyMentalInfoEntity> entities = fetchStockMentalInfoFromLixingren(requestJson);
            obj = entities;
            success = true;
            msg = "抓取成功";
        }
        return new ResultVo(success,obj,msg);
    }

    /**
     * 根据日期，查找stockDailyMentalInfo表中，字段date为指定日期的数据
     * @param date 指定日期，比如你传的时间是2018-04-01 11:10:00，那么这里只判断到日期部分，也就是2018-04-01
     * @return
     */
    public List<StockDailyMentalInfoEntity> findDatasByDate(Date date){
        if(date != null){
            String dateStr = DateUtils.convertDate2String(date,"yyyy-MM-dd");
            Wrapper<StockDailyMentalInfoEntity> wrapper = new EntityWrapper<>();
            wrapper.where("CAST(date as date)={0}",dateStr);
            List<StockDailyMentalInfoEntity> entities = baseMapper.selectList(wrapper);
            return entities;
        }
        return Lists.newArrayList();
    }

    public List<StockDailyMentalInfoEntity> findDatasByDateAndStockCode(Date date, String stockCode){
        if(date != null){
            String dateStr = DateUtils.convertDate2String(date,"yyyy-MM-dd");
            Wrapper<StockDailyMentalInfoEntity> wrapper = new EntityWrapper<>();
            wrapper.where("CAST(date as date)={0}",dateStr).and("stock_code='"+stockCode+"'");
            List<StockDailyMentalInfoEntity> entities = baseMapper.selectList(wrapper);
            //baseMapper.selectCount()
            return entities;
        }
        return Lists.newArrayList();
    }

    public int findCountByDateAndStockCode(Date date, String stockCode){
        int res = 0;
        if(date != null && !Strings.isNullOrEmpty(stockCode)){
            String dateStr = DateUtils.convertDate2String(date,"yyyy-MM-dd");
            Wrapper<StockDailyMentalInfoEntity> wrapper = new EntityWrapper<>();
            wrapper.where("CAST(date as date)={0}",dateStr).and("stock_code='"+stockCode+"'");
            List<StockDailyMentalInfoEntity> entities = baseMapper.selectList(wrapper);
            res = baseMapper.selectCount(wrapper);
        }
        return res;
    }

    /**
     * 根据taskType,taskStatus和条数，获取符合条件的股票日常抓取任务
     * @param taskType
     * @param taskStatus
     * @param limit
     * @return
     */
    public List<StockDailyCrawlTaskEntity> getCrawlTaskByTypeAndStatusAndLimit(int taskType, int taskStatus, int limit){
        Wrapper<StockDailyCrawlTaskEntity> wrapper = new EntityWrapper<>();
        wrapper.where("task_status={0}",taskStatus).and("task_type="+taskType).orderBy("crawl_date",true);
        if(limit > 0)
            wrapper.last("limit "+limit);

        return stockDailyCrawlTaskDao.selectList(wrapper);
    }

    /**
     * 根据stockCode，依据date字段正序获取所有
     * @param stockCode
     * @return
     */
    public List<StockDailyMentalInfoEntity> findDatasByStocCodeOrderByDateAsc(String stockCode){
        List<StockDailyMentalInfoEntity> entities = Lists.newArrayList();
        if(!Strings.isNullOrEmpty(stockCode)){
            Wrapper<StockDailyMentalInfoEntity> wrapper = new EntityWrapper<>();
            wrapper.where("stock_code={0}",stockCode).orderBy("date",true);
            entities = baseMapper.selectList(wrapper);
        }
        return entities;
    }

    /**
     * 计算每条记录的股票温度
     * @param entities
     * @return 返回的结果是计算前没有，但计算后有了温度的数据，也就是它可能≤参数entities
     */
    public List<StockDailyMentalInfoEntity> computeStockDailyMentalInfoDatasDegree(List<StockDailyMentalInfoEntity> entities){
        logger.info("计算股票温度的程序开始时间为："+System.currentTimeMillis());
        List<StockDailyMentalInfoEntity> ress = Lists.newArrayList();
        //double[] pb_array = new double[entities.size()];
        //double[] pe_array = new double[entities.size()];
        List<Double> pbList = Lists.newArrayList();
        List<Double> peList = Lists.newArrayList();
        int index = 0;
        //整理出全部的pb、pe以及待计算股票温度的数据
        for(StockDailyMentalInfoEntity entity : entities){
            if(entity.getPeTtm() == null || entity.getPb()==null){
                //不参与计算，但是状态改变
                if(entity.getDegreed()==0){
                    entity.setDegreed(1);
                    ress.add(entity);
                }

                continue;
            }
            pbList.add(entity.getPb()==null?0d:entity.getPb().doubleValue());
            peList.add(entity.getPeTtm()==null?0d:entity.getPeTtm().doubleValue());
            //pe_array[index] = entity.getPeTtm()==null?0d:entity.getPeTtm().doubleValue();
            //pb_array[index] = entity.getPb()==null?0d:entity.getPb().doubleValue();
            index ++;
            if(entity.getDegreed() == 0 && index <= 1){
                //第一个数据不计算温度，但要改变状态
                entity.setDegreed(1);
                ress.add(entity);
            }
            if(entity.getDegreed() == 0 && index > 1){
                try{
                    double peTtmDegree = MathUtils.pbOrPeDegree(BaseClassConvertUtils.convertDoubleList2Array(peList),entity.getPeTtm().doubleValue(),0,index);
                    double pbDegree = MathUtils.pbOrPeDegree(BaseClassConvertUtils.convertDoubleList2Array(pbList),entity.getPb().doubleValue(),0,index);
                    double stockDegree = (peTtmDegree+pbDegree)/2;
                    entity.setDegreed(1);
                    entity.setPeTtmDegree(new BigDecimal(peTtmDegree));
                    entity.setPbDegree(new BigDecimal(pbDegree));
                    entity.setStockDegree(new BigDecimal(stockDegree));
                    ress.add(entity);
                }
                catch (Exception e){
                    ress.clear();
                    logger.info("error",e);
                    break;
                }

//                DegreeComputeTempClass dct = new DegreeComputeTempClass();
//                dct.length = index;
//                dct.stockDailyMentalInfoEntity = entity;
//                dcts.add(dct);
            }
        }
        //开始计算
//        for(DegreeComputeTempClass dct : dcts){
//            double peTtmDegree = MathUtils.pbOrPeDegree(pe_array,dct.stockDailyMentalInfoEntity.getPeTtm().doubleValue(),0,dct.length);
//            double pbDegree = MathUtils.pbOrPeDegree(pe_array,dct.stockDailyMentalInfoEntity.getPb().doubleValue(),0,dct.length);
//            double stockDegree = (peTtmDegree+pbDegree)/2;
//            dct.stockDailyMentalInfoEntity.setDegreed(1);
//            dct.stockDailyMentalInfoEntity.setPeTtmDegree(new BigDecimal(peTtmDegree));
//            dct.stockDailyMentalInfoEntity.setPbDegree(new BigDecimal(pbDegree));
//            dct.stockDailyMentalInfoEntity.setStockDegree(new BigDecimal(stockDegree));
//            ress.add(dct.stockDailyMentalInfoEntity);
//        }
        logger.info("计算股票温度的程序结束时间为："+System.currentTimeMillis());
        return ress;
    }


    private class DegreeComputeTempClass {
        StockDailyMentalInfoEntity stockDailyMentalInfoEntity;
        int length;
    }

    /**
     * 批量插入或者更新
     * @param entities
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean batchUpdateStockDailyMentalInfo(List<StockDailyMentalInfoEntity> entities){
        boolean res = false;
        try{
            insertOrUpdateBatch(entities);
            res = true;
        }
        catch(Exception e){
            logger.info("error:",e);
        }
        return res;
    }

}
