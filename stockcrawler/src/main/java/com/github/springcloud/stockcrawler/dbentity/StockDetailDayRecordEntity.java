package com.github.springcloud.stockcrawler.dbentity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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

    @Column(name = "jin_kai")
    private BigDecimal jinKai;

    @Column(name = "zuo_shou")
    private BigDecimal zuoShou;

    @Column(name = "cheng_jiao_liang")
    private BigDecimal chengJiaoLiang;

    @Column(name = "huan_shou_lv")
    private BigDecimal huanShouLv;

    @Column(name = "zui_gao")
    private BigDecimal zuiGao;

    @Column(name = "zui_di")
    private BigDecimal zuiDi;

    @Column(name = "zhang_ting")
    private BigDecimal zhangTing;

    @Column(name = "die_ting")
    private BigDecimal dieTing;

    @Column(name = "nei_pan")
    private BigDecimal neiPan;

    @Column(name = "wai_pan")
    private BigDecimal waiPan;

    @Column(name = "cheng_jiao_e")
    private BigDecimal chengJiaoE;

    @Column(name = "zhen_fu")
    private BigDecimal zhenFu;

    @Column(name = "wei_bi")
    private BigDecimal weiBi;

    @Column(name = "liang_bi")
    private BigDecimal liangBi;

    @Column(name = "liu_tong_shi_zhi")
    private BigDecimal liuTongShiZhi;

    @Column(name = "zong_shi_zhi")
    private BigDecimal zongShiZhi;

    @Column(name = "shi_ying_lv")
    private BigDecimal shiYingLv;

    @Column(name = "shi_jing_lv")
    private BigDecimal shiJingLv;

    @Column(name = "mei_gu_shou_yi")
    private BigDecimal meiGuShouYi;

    @Column(name = "mei_gu_jing_zi_chan")
    private BigDecimal meiGuJingZiChan;

    @Column(name = "zong_gu_ben")
    private BigDecimal zongGuBen;

    @Column(name = "liu_tong_gu_ben")
    private BigDecimal liuTongGuBen;
}
