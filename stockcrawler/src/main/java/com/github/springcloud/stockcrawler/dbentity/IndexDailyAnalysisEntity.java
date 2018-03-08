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

    @Column(name = "index_id")
    private String indexId;

    @Column(name = "pe_ttm")
    private BigDecimal peTtm;

    @Column(name = "pb")
    private BigDecimal pb;

    @Column(name = "pe_degree")
    private BigDecimal peDegree;

    @Column(name = "funding_degree")
    private BigDecimal fundingDegree;

    @Column(name = "analysis_time")
    private Date analysisTime;
}
