package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.StockDailyCrawlTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by ganzhen on 2018/4/28.
 */
public interface StockDailyCrawlTaskDao extends SuperMapperPlus<StockDailyCrawlTaskEntity> {

    /**
     * 根据id更新taskStatus
     * @param params key：id、taskStatus
     * @return
     */
    public int updateTaskStatusById(Map<String,Object> params);

    /**
     * 批量插入数据
     * @param entities
     * @return
     */
    public int batchInsertList(List<StockDailyCrawlTaskEntity> entities);
}
