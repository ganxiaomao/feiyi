package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 股票详情每日的记录
 * Created by ganzhen on 13/02/2018.
 */
@Table(name = "financial_stock_detail_day_record")
@Data
public class StockDetailDayRecordEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
    private String id;

    @Column(name = "stock_code")
    private String stockCode;

    /**
     * 今日开市股价
     */
    @Column(name = "jin_kai")
    private BigDecimal jinKai;

    /**
     * 昨日收市股价
     */
    @Column(name = "zuo_shou")
    private BigDecimal zuoShou;

    /**
     * 成交量，单位：万手
     */
    @Column(name = "cheng_jiao_liang")
    private BigDecimal chengJiaoLiang;

    /**
     * 换手率，单位：%
     */
    @Column(name = "huan_shou_lv")
    private BigDecimal huanShouLv;

    /**
     * 最高股价，单位：元
     */
    @Column(name = "zui_gao")
    private BigDecimal zuiGao;

    /**
     * 最低股价，单位：元
     */
    @Column(name = "zui_di")
    private BigDecimal zuiDi;

    /**
     * 涨停股价，单位：元
     */
    @Column(name = "zhang_ting")
    private BigDecimal zhangTing;

    /**
     * 跌停股价，单位：元
     */
    @Column(name = "die_ting")
    private BigDecimal dieTing;

    /**
     * 内盘成交量，单位：万手
     */
    @Column(name = "nei_pan")
    private BigDecimal neiPan;

    /**
     * 外盘成交量，单位：万手
     */
    @Column(name = "wai_pan")
    private BigDecimal waiPan;

    /**
     * 成交额，单位：亿
     */
    @Column(name = "cheng_jiao_e")
    private BigDecimal chengJiaoE;

    /**
     * 振幅，单位：%
     */
    @Column(name = "zhen_fu")
    private BigDecimal zhenFu;

    /**
     * 委比，单位：%。委比是衡量一段时间内场内买、卖盘强弱的技术指标。
     * 它的计算公式为：委比＝（委买手数－委卖手数）／（委买手数＋委卖手数）×100％。
     * 从公式中可以看出，“委比”的取值范围从－100％至＋100％。若“委比”为正值，说明场内买盘较强，且数值越大，买盘就越强劲。
     * 反之，若“委比”为负值，则说明市道较弱。
     */
    @Column(name = "wei_bi")
    private BigDecimal weiBi;

    /**
     * 量比。量比=现成交总手/(过去5日平均每分钟成交量×当日累计开市时间(分))
     * 当量比大于1时，说明当日每分钟的平均成交量要大于过去5日的平均数值，交易比过去5日火爆;
     * 而当量比小于1时，说明现在的成交比不上过去5日的平均水平。
     */
    @Column(name = "liang_bi")
    private BigDecimal liangBi;

    /**
     * 流通市值，单位：亿
     */
    @Column(name = "liu_tong_shi_zhi")
    private BigDecimal liuTongShiZhi;

    /**
     * 总市值，单位：亿
     */
    @Column(name = "zong_shi_zhi")
    private BigDecimal zongShiZhi;

    /**
     * 市盈率，PE
     */
    @Column(name = "shi_ying_lv")
    private BigDecimal shiYingLv;

    /**
     * 市净率，PB
     */
    @Column(name = "shi_jing_lv")
    private BigDecimal shiJingLv;

    /**
     * 每股收益
     */
    @Column(name = "mei_gu_shou_yi")
    private BigDecimal meiGuShouYi;

    /**
     * 每股净资产
     */
    @Column(name = "mei_gu_jing_zi_chan")
    private BigDecimal meiGuJingZiChan;

    /**
     * 总股本，单位：亿
     */
    @Column(name = "zong_gu_ben")
    private BigDecimal zongGuBen;

    /**
     * 流通股本，单位：亿
     */
    @Column(name = "liu_tong_gu_ben")
    private BigDecimal liuTongGuBen;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
