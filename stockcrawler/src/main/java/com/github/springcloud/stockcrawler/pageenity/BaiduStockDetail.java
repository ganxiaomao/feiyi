package com.github.springcloud.stockcrawler.pageenity;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by ganzhen on 13/02/2018.
 */
@Gecco(matchUrl = "https://gupiao.baidu.com/stock/{code}.html",pipelines = {"stockStarComPipeline","consolePipeline"})
public class BaiduStockDetail implements HtmlBean {

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(1) > dd")
    private String jin_kay;//今日开市股价

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(1) > dd")
    private String zuo_shou;//昨日收市股价

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(2) > dd")
    private String cheng_jiao_liang;//成交量，单位：万手

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(2) > dd")
    private String huan_shou_lv;//换手率

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(3) > dd")
    private String zui_gao;//最高价格

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(3) > dd")
    private String zui_di;//最低价格

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(4) > dd")
    private String zhang_ting;//涨停价格

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(4) > dd")
    private String die_ting;//跌停价格

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(5) > dd")
    private String nei_pan_shou_shu;//内盘，单位：万手

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(5) > dd")
    private String wai_pan_shou_shu;//外盘，单位：万手

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(6) > dd")
    private String cheng_jiao_e;//成交额，单位：亿

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(6) > dd")
    private String zhen_fu;//振幅

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(7) > dd")
    private String wei_bi;//委比

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(7) > dd")
    private String liang_bi;//量比

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(8) > dd")
    private String liu_tong_shi_zhi;//流通市值，单位：亿

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(8) > dd")
    private String zong_shi_zhi;//总市值，单位：亿

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(9) > dd")
    private String shi_ying_lv;//市盈率，PE，也称为“利润收益率”,是某种股票普通股每股市价与每股盈利的比率,即：PE(市盈率)=每股市价/每股收益

    /**
     * 市净率，PB，平均市净率=股价/账面价值。其中,账面价值=总资产-无形资产-负债-优先股权益。
     * 可以看出,所谓账面价值是公司解散清算的价值。如果公司要清算,那么先要还债,无形资产则将不复存在,
     * 而优先股的优先权之一就是清算的时候先分钱,但是本股市没有优先股。这样,用每股净资产来代替账面价值,
     * 则PB就是大家理解的市净率,即：PB(市净率)=股价/每股净资产
     */
    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(9) > dd")
    private String shi_jing_lv;//

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(10) > dd")
    private String mei_gu_shou_yi;//每股收益

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(10) > dd")
    private String mei_gu_jing_zi_chan;//每股净资产

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line1 > dl:nth-child(11) > dd")
    private String zong_gu_ben;//总股本，单位：亿

    @Text
    @HtmlField(cssPath = "#app-wrap > div.stock-info > div > div.bets-content > div.line2 > dl:nth-child(11) > dd")
    private String liu_tong_gu_ben;//流通股本，单位：亿
}
