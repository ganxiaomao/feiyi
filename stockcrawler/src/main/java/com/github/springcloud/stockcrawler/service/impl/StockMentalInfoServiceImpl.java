package com.github.springcloud.stockcrawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eaio.uuid.UUID;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.github.springcloud.basecommon.utils.DateUtils;
import com.github.springcloud.stockcrawler.common.LixingerUtils;
import com.github.springcloud.stockcrawler.dbdao.StockDailyMentalInfoDao;
import com.github.springcloud.stockcrawler.dbentity.StockDailyMentalInfoEntity;
import com.github.springcloud.stockcrawler.service.StockMentalInfoService;
import com.github.springcloud.stockcrawler.vo.ResultVo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ganzhen on 2018/4/28.
 */
@Service("stockMentalInfoServiceImpl")
public class StockMentalInfoServiceImpl extends ServiceImpl<StockDailyMentalInfoDao,StockDailyMentalInfoEntity> implements StockMentalInfoService {
    Logger logger = LoggerFactory.getLogger(StockMentalInfoServiceImpl.class);

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
        String msg = dateStr+"日，股票基本面数据获取成功";
        String requestJson = LixingerUtils.arrangeLixingerStockMentalInfoParam(dateStr,null);
        List<StockDailyMentalInfoEntity> entities = fetchStockMentalInfoFromLixingren(requestJson);
        //批量保存
        try{
            //先根据日期查找符合的基本面信息记录，如果有的话，就不再生成记录

            baseMapper.batchInsertList(entities);
        }
        catch(Exception e){
            success = false;
            msg = dateStr+"日，股票基本面数据获取失败，失败原因："+e.getMessage();
            logger.info("error:",e);
        }

        return new ResultVo(success,null,msg);
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
}
