package com.github.springcloud.stockcrawler.dbentity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ganzhen on 11/02/2018.
 */
//@Table(name = "financial_stock_base_info")
@TableName("financial_stock_base_info")
@Data
public class StockBaseInfoEntity extends Model<StockBaseInfoEntity> {
//    @Id
//    @Column(name = "Id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    @TableId(type= IdType.UUID)
    @TableField(value = "id")
    private String id;

    /**
     * 股票名称
     */
    //@Column(name = "stock_name")
    @TableField(value = "stock_name")
    private String stockName;

    /**
     * 股票代码
     */
    //@Column(name = "stock_code")
    @TableField(value = "stock_code")
    private String stockCode;

    /**
     * 1,上证；2，深证
     */
    //@Column(name = "stock_type")
    @TableField(value = "stock_type")
    private Integer stockType;

    //@Column(name = "create_time")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 上市时间
     */
    //@Column(name = "listed_time")
    @TableField(value = "listed_time")
    private Date listedTime;

    /**
     * 是否退市：1，是；0，否
     */
    //@Column(name = "delist")
    @TableField(value = "delist")
    private int delist = 0;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
