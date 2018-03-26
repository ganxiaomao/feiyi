package com.github.springcloud.teccom.tkmybatis;

import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * Created by ganzhen on 26/03/2018.
 */
public class TkMapperConfig {
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.github.springcloud.teccom.dbdao");
        Properties propertiesMapper = new Properties();
        //通用mapper位置，不要和其他mapper、dao放在同一个目录
        propertiesMapper.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
        propertiesMapper.setProperty("notEmpty", "false");
        //主键UUID回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
        propertiesMapper.setProperty("ORDER","BEFORE");
        propertiesMapper.setProperty("IDENTITY","SELECT REPLACE(UUID(),'-','')");
        mapperScannerConfigurer.setProperties(propertiesMapper);
        return mapperScannerConfigurer;
    }
}
