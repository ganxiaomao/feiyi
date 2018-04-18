package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ganzhen on 18/04/2018.
 */
@Table(name = "financial_stock_daily_mental_info")
@Data
public class StockDailyMentalInfoEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 动态市盈率
     */
    @Column(name = "pe_ttm")
    private BigDecimal pe_ttm;

    /**
     * 扣非动态市盈率
     */
    @Column(name = "d_pe_ttm")
    private BigDecimal d_pe_ttm;

    /**
     * 市净率
     */
    @Column(name = "pb")
    private BigDecimal pb;

    /**
     * 不含商誉的市净率
     */
    @Column(name = "pb_wo_gw")
    private BigDecimal pb_wo_gw;

    /**
     * 滚动市销率
     */
    @Column(name = "ps_ttm")
    private BigDecimal ps_ttm;

    /**
     * 股息率
     */
    @Column(name = "dividend_r")
    private BigDecimal dividend_r;

    /**
     * 市值
     */
    @Column(name = "market_value")
    private BigDecimal market_value;

    /**
     * 股票编码
     */
    @Column(name = "stock_code")
    private String stockCode;

    /**
     * 数据产生的时间
     */
    @Column(name = "date")
    private Date date;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
