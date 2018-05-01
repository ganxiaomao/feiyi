package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.StockBaseInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by ganzhen on 11/02/2018.MyMapper
 */
public interface StockBaseInfoDao extends SuperMapperPlus<StockBaseInfoEntity> {

    public StockBaseInfoEntity selectOneByStockCode(String stockCode);

    public List<StockBaseInfoEntity> selectDatasByStockName(String stockName);

    public int batchInsert(List<StockBaseInfoEntity> list);

    /**
     * 批量更新delist状态
     * @param params key：delist，更新的值；list，stockCode集合
     * @return
     */
    public int batchUpdateDelistByStockCodes(Map<String,Object> params);

    /**
     * 根据delist获取所有符合的stockCode
     * @param delist
     * @return
     */
//    public List<String> selectAllStockCodesByDelist(int delist);

}
