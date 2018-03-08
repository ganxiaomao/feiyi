package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 指数-基金关系表(一对多关系，指数唯一，基金多个)
 * Created by ganzhen on 08/03/2018.
 */
@Table(name = "financial_funding_index_refer")
@Data
public class FundingIndexReferEntity implements Serializable {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    /**
     * 基金id
     */
    @Column(name = "funding_id")
    private String fundingId;

    /**
     * 指数id
     */
    @Column(name = "index_id")
    private String indexId;

}
