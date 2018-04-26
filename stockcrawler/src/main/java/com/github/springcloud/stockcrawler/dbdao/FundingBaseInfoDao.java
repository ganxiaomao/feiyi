package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.MyMapper;
import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.FundingBaseInfoEntity;

import java.util.List;

/**
 * 基金基本信息dao
 * Created by ganzhen on 08/03/2018.
 */
public interface FundingBaseInfoDao extends SuperMapperPlus<FundingBaseInfoEntity> {

    /**
     * 根据基金code查找基金
     * @param fundCode
     * @return
     */
    public FundingBaseInfoEntity selectOneByFundCode(String fundCode);

    /**
     * 根据基金名称查找基金
     * @param name
     * @return
     */
    public List<FundingBaseInfoEntity> selectDatasByName(String name);

    /**
     * 根据基金公司查找基金
     * @param company
     * @return
     */
    public List<FundingBaseInfoEntity> selectDatasByCompany(String company);
}
