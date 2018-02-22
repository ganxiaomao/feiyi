package com.github.springcloud.stockcrawler.service.impl;

import com.github.springcloud.stockcrawler.service.QuartzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ganzhen on 22/02/2018.
 */
@Service("quartzServiceImpl")
public class QuartzServiceImpl implements QuartzService {
    Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);

    @Override
    public String addJob2Mysql(String jobClassName, String jobGroupName, String cornExpression) {
        return null;
    }
}
