package com.github.springcloud.stockcrawler.constant;

/**
 * 股票日常相关常量
 */
public class StockDailyConstant {
    /**
     * 股票日常抓取任务类型：基本面数据
     */
    public static final int stock_daily_crawl_task_type_mental = 1;
    /**
     * 股票日常抓取任务状态：未抓取
     */
    public static final int stock_daily_crawl_task_status_undo = 0;
    /**
     * 股票日常抓取任务状态：抓取失败
     */
    public static final int stock_daily_crawl_task_status_fail = -1;
    /**
     * 股票日常抓取任务状态：抓取成功
     */
    public static final int stock_daily_crawl_task_status_success = 1;
}
