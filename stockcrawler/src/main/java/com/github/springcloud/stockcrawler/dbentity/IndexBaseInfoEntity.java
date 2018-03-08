package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 指数基本信息表
 * Created by ganzhen on 08/03/2018.
 */
@Table(name = "financial_index_base_info")
@Data
public class IndexBaseInfoEntity implements Serializable{

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 指数名称
     */
    @Column(name = "index_name")
    private String indexName;

    /**
     * 指数代码
     */
    @Column(name = "index_code")
    private String indexCode;

    /**
     * 指数创立时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 记录生成时间
     */
    @Column(name = "record_time")
    private Date recordTime;

    /**
     * 记录状态：0，正常；-1，无效
     */
    @Column(name = "status")
    private int status;
}
