package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ganzhen on 11/02/2018.
 */
@Table(name = "financial_stock_base_info")
@Data
public class StockBaseInfoEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 股票名称
     */
    @Column(name = "stock_name")
    private String stockName;

    /**
     * 股票代码
     */
    @Column(name = "stock_code")
    private String stockCode;

    /**
     * 1,上证；2，深证
     */
    @Column(name = "stock_type")
    private Integer stockType;

    @Column(name = "create_time")
    private Date createTime;
}
