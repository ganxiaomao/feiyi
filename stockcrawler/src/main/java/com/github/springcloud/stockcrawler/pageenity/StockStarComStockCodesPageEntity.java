package com.github.springcloud.stockcrawler.pageenity;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

import java.util.List;

/**
 * 证券之星网站的股票全部代码页面实体类，对应地址（http://quote.stockstar.com/stock/stock_index.htm）
 */
@Gecco(matchUrl = "http://quote.stockstar.com/stock/stock_index.htm",pipelines = {"consolePipeline","stockStarComStockCodesPipeline"})
public class StockStarComStockCodesPageEntity implements HtmlBean {
    private static final long serialVersionUID = 1L;

    @Request
    private HttpRequest request;

    @HtmlField(cssPath = "body > div.w > div > div > div.seo_keywordsCon > ul.seo_pageList > li")
    private List<StockStarCodeAndName> sscns;

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public List<StockStarCodeAndName> getSscns() {
        return sscns;
    }

    public void setSscns(List<StockStarCodeAndName> sscns) {
        this.sscns = sscns;
    }
}
