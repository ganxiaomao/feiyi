package com.github.springcloud.basecommon.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ganzhen on 07/03/2018.
 */
public class TestMain {

    public static void main(String[] args){
        String url = "https://www.lixinger.com/api/open/a/indice/fundamental-info";
        Map<String,String> params = new HashMap<String,String>();
        params.put("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        params.put("date","2018-03-06");
        //params.put("stockCodes", JSONObject.toJSONString(Arrays.asList("399805")));
        //params.put("metrics",JSONObject.toJSONString(Arrays.asList("pe_ttm","pb")));


        JSONArray ja1 = new JSONArray();
        ja1.add("399805");

        JSONArray ja2 = new JSONArray();
        ja2.add("pe_ttm");
        ja2.add("pb");
        params.put("stockCodes", ja1.toJSONString());
        params.put("metrics",ja2.toJSONString());
        System.out.println(params.toString());
        params.put("stockCodes",ja1.toJSONString());
        params.put("metrics",ja2.toJSONString());

        JSONArray ja3 = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        jo.put("date","2018-03-06");
        ja3.add("pe_ttm");
        ja3.add("pb");
        jo.put("metrics",ja3);
        JSONArray ja4 = new JSONArray();
        ja4.add("399805");
        jo.put("stockCodes",ja4);

        /*Gson gson = new Gson();
        JsonObject jo1 = new JsonObject();
        jo1.addProperty("token","9bcbd411-608e-4ad2-8fb7-870ace1652e6");
        jo1.addProperty("date","2018-03-06");
        JsonArray ja3 = new JsonArray();
        ja3.add("399805");
        jo1.add("stockCodes",ja3);
        JsonArray ja4 = new JsonArray();
        ja4.add("pe_ttm");
        ja4.add("pb");
        jo1.add("metrics",ja4);*/

        System.out.println(jo.toJSONString()+";");
        //HttpUtils.httpPostBody(url,jo.toJSONString());
        HttpUtils.httpPost(url,"",params);
    }
}

