package com.github.springcloud.stockcrawler.dbentity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ganzhen on 18/04/2018.
 */
//@Table(name = "financial_stock_daily_mental_info")
@TableName("financial_stock_daily_mental_info")
@Data
public class StockDailyMentalInfoEntity extends Model<StockDailyMentalInfoEntity>  {
    //@Id
    //@Column(name = "Id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    @TableId(type= IdType.UUID)
    @TableField(value = "id")
    private String id;

    /**
     * 动态市盈率
     */
    //@Column(name = "pe_ttm")
    @TableField(value = "pe_ttm")
    private BigDecimal pe_ttm;

    /**
     * 扣非动态市盈率
     */
    //@Column(name = "d_pe_ttm")
    @TableField(value = "d_pe_ttm")
    private BigDecimal d_pe_ttm;

    /**
     * 市净率
     */
    //@Column(name = "pb")
    @TableField(value = "pb")
    private BigDecimal pb;

    /**
     * 不含商誉的市净率
     */
    //@Column(name = "pb_wo_gw")
    @TableField(value = "pb_wo_gw")
    private BigDecimal pb_wo_gw;

    /**
     * 滚动市销率
     */
    //@Column(name = "ps_ttm")
    @TableField(value = "ps_ttm")
    private BigDecimal ps_ttm;

    /**
     * 股息率
     */
    //@Column(name = "dividend_r")
    @TableField(value = "dividend_r")
    private BigDecimal dividend_r;

    /**
     * 市值
     */
    //@Column(name = "market_value")
    @TableField(value = "market_value")
    private BigDecimal market_value;

    /**
     * 股票编码
     */
    //@Column(name = "stock_code")
    @TableField(value = "stock_code")
    private String stockCode;

    /**
     * 数据产生的时间
     */
    //@Column(name = "date")
    @TableField(value = "date")
    private Date date;

    /**
     * 创建时间
     */
    //@Column(name = "create_time")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 是否信息抓取过：0，否；1，是
     */
    //@Column(name = "crawled")
    @TableField(value = "crawled")
    private int crawled = 0;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 重写equals函数，为了便于使用List中的contains函数
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof StockDailyMentalInfoEntity){
            StockDailyMentalInfoEntity tmp = (StockDailyMentalInfoEntity) o;
            return this.stockCode.equals(tmp.getStockCode());
        }
        return super.equals(o);
    }
}
