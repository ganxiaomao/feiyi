package com.github.springcloud.stockcrawler.service;

/**
 * Created by ganzhen on 22/02/2018.
 */
public interface QuartzService {
    /**
     * 增加job录入到mysql中
     * @param jobClassName
     * @param jobGroupName
     * @param cornExpression
     * @return
     */
    public String addJob2Mysql(String jobClassName, String jobGroupName, String cornExpression);
}
