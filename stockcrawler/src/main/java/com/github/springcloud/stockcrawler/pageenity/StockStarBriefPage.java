package com.github.springcloud.stockcrawler.pageenity;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * Created by ganzhen on 18/04/2018.
 */
@Gecco(matchUrl = "http://stock.quote.stockstar.com/corp/brief_{stockCode}.shtml",pipelines = {"stockStarComPipeline","consolePipeline"})
public class StockStarBriefPage implements HtmlBean {
    private static final long serialVersionUID = -1L;

    @RequestParameter("stockCode")
    private String stockCode;//url中的{stockCode}的值

    @Text
    @HtmlField(cssPath = "table.globalTable>tbody>tr:nth-child(1)>td:nth-child(3)")
    private String listedTime;//上市时间


    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getListedTime() {
        return listedTime;
    }

    public void setListedTime(String listedTime) {
        this.listedTime = listedTime;
    }
}
