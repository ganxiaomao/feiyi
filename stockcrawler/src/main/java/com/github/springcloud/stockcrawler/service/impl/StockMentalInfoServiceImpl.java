package com.github.springcloud.stockcrawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eaio.uuid.UUID;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.common.LixingerUtils;
import com.github.springcloud.stockcrawler.constant.StockDailyConstant;
import com.github.springcloud.stockcrawler.dbdao.StockDailyCrawlTaskDao;
import com.github.springcloud.stockcrawler.dbdao.StockDailyMentalInfoDao;
import com.github.springcloud.stockcrawler.dbentity.StockDailyCrawlTaskEntity;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ganzhen on 2018/4/28.
 */
@Service("stockMentalInfoServiceImpl")
public class StockMentalInfoServiceImpl extends ServiceImpl<StockDailyMentalInfoDao,StockDailyMentalInfoEntity> implements StockMentalInfoService {
    Logger logger = LoggerFactory.getLogger(StockMentalInfoServiceImpl.class);

    @Resource
    private StockDailyCrawlTaskDao stockDailyCrawlTaskDao;

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
        String dateStr = DateUtils.convertDate2String(date,"yyyy-MM-dd");
        boolean success = true;
        Object obj = null;
        String msg = dateStr+"日，股票基本面数据获取成功";
        String requestJson = LixingerUtils.arrangeLixingerStockMentalInfoParam(dateStr,null);
        List<StockDailyMentalInfoEntity> entities = fetchStockMentalInfoFromLixingren(requestJson);
        //批量保存
        try{
            //先根据日期查找符合的基本面信息记录，如果有的话，就不再生成记录
            List<StockDailyMentalInfoEntity> exists = findDatasByDate(date);
            List<StockDailyMentalInfoEntity> tmps = Lists.newArrayList();
            for(StockDailyMentalInfoEntity entity : entities){
                if(!exists.contains(entity))
                    tmps.add(entity);
            }
            obj = tmps;
            logger.info(dateStr+"日，抓取到数据"+entities.size()+"条，去除已存在的，总共"+tmps.size()+"条数据。");
        }
        catch(Exception e){
            success = false;
            msg = dateStr+"日，股票基本面数据获取失败，失败原因："+e.getMessage();
            logger.info("error:",e);
        }

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
    public ResultVo createStockDailyCrawlTask(Date date) {
        return null;
    }

    @Override
    public boolean batchInsertStockDailyCrawlTask(List<StockDailyCrawlTaskEntity> entities) {
        stockDailyCrawlTaskDao.batchInsertList(entities);
        return false;
    }

    @Override
    public ResultVo exeCrawlStockMentalInfoTask() {
        logger.info("开始执行抓取股票基本面信息任务");
        //首先查找crawltask表中未被抓取的数据
        int count = 0;
        while(true){
            count++;
            logger.info("抓取第"+count+"次的10条任务对应的信息");
            List<StockDailyCrawlTaskEntity> stockDailyCrawlTaskEntities = getCrawlTaskByTypeAndStatusAndLimit(StockDailyConstant.stock_daily_crawl_task_type_mental,StockDailyConstant.stock_daily_crawl_task_status_undo,10);
            List<String> success_ids = Lists.newArrayList();
            List<String> fail_ids = Lists.newArrayList();
            for(StockDailyCrawlTaskEntity stockDailyCrawlTaskEntity : stockDailyCrawlTaskEntities){
                ResultVo rv = crawlStockDailyMentalInfoFromLixinger(stockDailyCrawlTaskEntity.getCrawlDate());
                if(rv.isSuccess()){
                    //
                    if(rv.getObj() != null){
                        baseMapper.batchInsertList((List<StockDailyMentalInfoEntity>) rv.getObj());
                        success_ids.add(stockDailyCrawlTaskEntity.getId());
                    }
                }
                else{
                    //
                    if(rv.getObj() != null){
                        fail_ids.add(stockDailyCrawlTaskEntity.getId());
                    }

                }
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
}
