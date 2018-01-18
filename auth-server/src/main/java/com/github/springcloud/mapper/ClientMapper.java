package com.github.springcloud.mapper;

import com.github.springcloud.entity.ClientEntity;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * Created by ganzhen on 18/01/2018.
 */
public interface ClientMapper extends Mapper<ClientEntity> {

    public ClientEntity selectOneByNameAndPassword(Map<String,Object> params);
}
