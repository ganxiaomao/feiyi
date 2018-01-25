package com.github.springcloud.basecommon.biz;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by ganzhen on 25/01/2018.
 */
public abstract class BaseBiz<M extends Mapper<T>,T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper){this.mapper = mapper;}

    public T selectOne(T entity){
        return mapper.selectOne(entity);
    }

    public T selectById(Object id){
        return mapper.selectByPrimaryKey(id);
    }

    public List<T> selectList(T entity){
        return mapper.select(entity);
    }

    public List<T> selectListAll(){
        return mapper.selectAll();
    }

    public Long selectCount(T entity){
        return new Long(mapper.selectCount(entity));
    }

    public void insert(T entity){
        mapper.insert(entity);
    }

    public void insertSelective(T entity){
        mapper.insertSelective(entity);
    }

    public int delete(T entity){
        return mapper.delete(entity);
    }

    public int deleteById(Object id){
        return mapper.deleteByPrimaryKey(id);
    }

    public int updateById(T entity){
        return mapper.updateByPrimaryKey(entity);
    }

    public int updateSelectiveById(T entity){
        return mapper.updateByPrimaryKeySelective(entity);
    }
    
}
