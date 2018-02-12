package com.github.springcloud.stockcrawler.pageenity;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HrefBean;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
public class StockStarCodeAndName implements HtmlBean {
    private static final long serialVersionUID = 3018760488621382659L;

    @Text
    @HtmlField(cssPath = "span > a")
    private String stockCode;

    @Href
    @HtmlField(cssPath = "span > a")
    private String stockCodeUrl;

    @Text
    @HtmlField(cssPath = "a")
    private String stockName;

    @HtmlField(cssPath = "a")
    private List<HrefBean> urls;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCodeUrl() {
        return stockCodeUrl;
    }

    public void setStockCodeUrl(String stockCodeUrl) {
        this.stockCodeUrl = stockCodeUrl;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public List<HrefBean> getUrls() {
        return urls;
    }

    public void setUrls(List<HrefBean> urls) {
        this.urls = urls;
    }
}
