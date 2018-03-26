package com.github.springcloud.basecommon.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.springcloud.basecommon.httputils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
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

        //System.out.println(jo.toJSONString()+";");
        //HttpUtils.httpPostBody(url,jo.toJSONString());
        //HttpUtils.httpPost(url,"",params);
        generateLoanRequestJson();
    }

    public static void generateLoanRequestJson(){
        //
        JSONObject jo = new JSONObject();
        jo.put("loanerIdNum","3111111");//借款用户身份证号码
        jo.put("loanAmount",new BigDecimal("100.02"));//借款金额
        jo.put("loanDuration",1);//借款期限，单位：天，如果是年月的话，按照一年12个月，每月30天来计算实际天数
        jo.put("investYieldRate",new BigDecimal("750"));//投资人年利率，万分比的数值部分
        jo.put("loanSerial","20180321001");//标的序列号，保证唯一性
        jo.put("channelCode","YDT");//渠道编号
        jo.put("productCode","001");//产品编号
        jo.put("projectCode","00001");//项目编号，对应微金融的busiCode
        jo.put("loanServerFeePeriod","SINGLE");//借款服务费收取方式
        jo.put("loanServerFeeRate",new BigDecimal("230"));//服务费收取费率，万分比
        jo.put("chargeUserIdNum","");//资金划转账户对应的身份编号
        jo.put("agentFeeRate",new BigDecimal("150"));//代理商服务费率，万分比
        jo.put("repayUserIdNum","002");//还款用户身份编号
        jo.put("repayMethod","RepaymentPlan");//还款方式

        Map<String,String> params = new HashMap<>();
        params.put("param1","参数1");
        params.put("param2","参数2");
        params.put("param3","参数4");
        jo.put("params",params);

        System.out.println(jo.toJSONString());
    }
}

