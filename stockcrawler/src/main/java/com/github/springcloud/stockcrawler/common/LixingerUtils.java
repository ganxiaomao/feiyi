package com.github.springcloud.stockcrawler.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 理杏仁接口相关的工具类
 * Created by ganzhen on 2018/4/28.
 */
public class LixingerUtils {

    /**
     *
     * @param date
     * @param stockCodes
     * @return
     */
    public static String arrangeLixingerStockMentalInfoParam(String date,List<String> stockCodes){
        JSONObject jo = new JSONObject();
        jo.put("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        jo.put("date",date);
        JSONArray ja3 = new JSONArray();
        ja3.add("pe_ttm");
        ja3.add("pb");
        ja3.add("market_value");
        ja3.add("dividend_r");
        ja3.add("d_pe_ttm");
        ja3.add("pb_wo_gw");
        ja3.add("ps_ttm");
        jo.put("metrics",ja3);
        //不添加stockCodes，则会返回所有未退市的股票基本面数据
        JSONArray ja4 = new JSONArray();
        if(stockCodes != null && !stockCodes.isEmpty()){
            for(String stockCode : stockCodes){
                ja4.add(stockCode);
            }
            jo.put("stockCodes",ja4);
        }
        return jo.toJSONString();
    }
}
