package com.github.springcloud.stockcrawler;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by ganzhen on 14/02/2018.
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
