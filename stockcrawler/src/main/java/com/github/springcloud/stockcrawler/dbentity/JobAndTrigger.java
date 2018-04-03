package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * quartz任务触发器集合数据
 * Created by ganzhen on 03/04/2018.
 */
@Data
public class JobAndTrigger implements Serializable {
    private String JOB_NAME;
    private String JOB_GROUP;
    private String JOB_CLASS_NAME;
    private String TRIGGER_NAME;
    private String TRIGGER_GROUP;
    private BigInteger REPEAT_INTERVAL;
    private BigInteger TIMES_TRIGGERED;
    private String CRON_EXPRESSION;
    private String TIME_ZONE_ID;
}
