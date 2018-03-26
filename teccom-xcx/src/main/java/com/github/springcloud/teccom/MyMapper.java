package com.github.springcloud.teccom;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by ganzhen on 26/03/2018.
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
