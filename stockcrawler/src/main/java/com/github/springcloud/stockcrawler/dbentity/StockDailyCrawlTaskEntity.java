package com.github.springcloud.stockcrawler.dbentity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日常股票信息抓取任务表对应的实体类
 * Created by ganzhen on 2018/4/28.
 */
@TableName("financial_stock_daily_crawl_task")
@Data
public class StockDailyCrawlTaskEntity extends Model<StockDailyCrawlTaskEntity> {
    @TableId(type= IdType.UUID)
    @TableField(value = "id")
    private String id;

    /**
     * 任务执行的日期
     */
    @TableField(value = "crawl_date")
    private Date crawlDate;

    /**
     * 抓取类型：1，股票基本面信息
     */
    @TableField(value = "task_type")
    private int taskType;

    /**
     * 任务状态：0，未执行；1，执行成功；-1，执行失败
     */
    @TableField(value = "task_status")
    private int taskStatus;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
