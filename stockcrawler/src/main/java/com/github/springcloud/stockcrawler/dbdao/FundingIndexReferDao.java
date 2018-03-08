package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.MyMapper;
import com.github.springcloud.stockcrawler.dbentity.FundingIndexReferEntity;

import java.util.List;

/**
 * Created by ganzhen on 08/03/2018.
 */
public interface FundingIndexReferDao extends MyMapper<FundingIndexReferEntity> {

    public FundingIndexReferEntity selectOneByIndexId(String indexId);

    public List<FundingIndexReferEntity> selectDatasByFundingId(String fundingId);
}
