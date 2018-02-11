package com.github.springcloud.stockcrawler.pagepipe;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.github.springcloud.stockcrawler.pageenity.StockStarCodeAndName;
import com.github.springcloud.stockcrawler.pageenity.StockStarComStockCodesPageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ganzhen on 11/02/2018.
 */
@Service("stockStarComStockCodesPipeline")
public class StockStarComPipeline implements Pipeline<StockStarComStockCodesPageEntity>{
    public static List<HttpRequest> cateRequests = new ArrayList<>();

    @Override
    public void process(StockStarComStockCodesPageEntity stockStarComStockCodesPageEntity) {
        List<StockStarCodeAndName> sscns = stockStarComStockCodesPageEntity.getSscns();
        System.out.println("抓取的数据数量："+sscns.size());
    }
}
