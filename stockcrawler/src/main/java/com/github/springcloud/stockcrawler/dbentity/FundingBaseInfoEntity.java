package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金基本信息表
 * Created by ganzhen on 08/03/2018.
 */
@Table(name = "financial_funding_base_info")
@Data
public class FundingBaseInfoEntity implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 基金名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 基金代码
     */
    @Column(name = "fund_code")
    private String fundCode;

    /**
     * 基金创立时间
     */
    @Column(name = "funding_time")
    private Date fundingTime;

    /**
     * 资产规模
     */
    @Column(name = "assets_scale")
    private BigDecimal assetsScale;

    /**
     * 基金类型
     */
    @Column(name = "fund_type")
    private String fundType;

    /**
     * 基金经理
     */
    @Column(name = "fund_manager")
    private String fundManager;

    /**
     * 基金公司
     */
    @Column(name = "fund_company")
    private String fundCompany;

    /**
     * 记录生成的时间
     */
    @Column(name = "record_time")
    private Date recordTime;

    /**
     * 状态：0，正常；-1，无效
     */
    @Column(name = "status")
    private int statues;

    /**
     * 近3年及以上的平均收益率
     */
    @Column(name = "lately_year_rate_return")
    private BigDecimal latelytYearRateReturn;
}
