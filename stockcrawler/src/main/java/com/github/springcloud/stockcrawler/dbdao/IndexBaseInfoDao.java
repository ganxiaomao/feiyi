package com.github.springcloud.stockcrawler.dbdao;

import com.github.springcloud.stockcrawler.MyMapper;
import com.github.springcloud.stockcrawler.SuperMapperPlus;
import com.github.springcloud.stockcrawler.dbentity.IndexBaseInfoEntity;

import java.util.List;

/**
 * Created by ganzhen on 08/03/2018.
 */
public interface IndexBaseInfoDao extends SuperMapperPlus<IndexBaseInfoEntity> {

    public IndexBaseInfoEntity selectOneByIndexCode(String indexCode);

    public List<IndexBaseInfoEntity> selectDatasByIndexName(String indexName);
}
