package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ganzhen on 08/03/2018.
 */
@Table(name = "financial_index_daily_analysis")
@Data
public class IndexDailyAnalysisEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 指数基本信息表id
     */
    @Column(name = "index_id")
    private String indexId;

    /**
     * 动态市净率
     */
    @Column(name = "pe_ttm")
    private BigDecimal peTtm;

    /**
     * 市盈率
     */
    @Column(name = "pb")
    private BigDecimal pb;

    /**
     * 市净率温度
     */
    @Column(name = "pe_degree")
    private BigDecimal peDegree;

    /**
     * 市盈率温度
     */
    @Column(name = "pb_degree")
    private BigDecimal pbDegree;

    /**
     * 指数温度
     */
    @Column(name = "index_degree")
    private BigDecimal indexDegree;

    @Column(name = "analysis_time")
    private Date analysisTime;
}
