package com.github.springcloud.basecommon.httputils;

import com.google.common.base.Strings;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ganzhen on 07/03/2018.
 */
public class HttpUtils {
    private static final String default_header_content_type = "application/json; charset=utf-8";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 普通的form表单提交形式
     * @param url
     * @param contentType 请求header的Content-Type，可以不传，默认为"application/json; charset=utf-8"
     * @param params
     * @return
     */
    public static String httpPost(String url,String contentType,Map<String,String> params){
        String responseStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> list = createNameValuePair(params);
        httpPost.setEntity(new UrlEncodedFormEntity(list));
        httpPost.addHeader("Content-Type", Strings.isNullOrEmpty(contentType)?default_header_content_type:contentType);
        httpPost.addHeader("Content-Encoding","gzip");
        HttpResult hr = excuteHttpRequest(httpClient,httpPost);
        return hr.responseStr;
    }

    /**
     * body提交形式
     * @param url
     * @param body body内容，一般是json字符串
     * @return
     */
    public static String httpPostBody(String url, String body){
        logger.info("url="+url+";body="+body);
        String responseStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", default_header_content_type);
        //httpPost.addHeader("Content-Encoding","gzip");
        httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));

        HttpResult hr = excuteHttpRequest(httpClient,httpPost);
        return hr.responseStr;
    }

    public static String httpGet(String url){
        String responseStr = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResult hr = excuteHttpRequest(httpClient,httpGet);

        return hr.responseStr;
    }

    public static HttpResult excuteHttpRequest(CloseableHttpClient httpClient,ClassicHttpRequest request){
        HttpResult hr = new HttpResult();
        try{
            CloseableHttpResponse reponse = httpClient.execute(request);
            HttpEntity entity = reponse.getEntity();
            int statusCode = reponse.getCode();
            String responseStr = EntityUtils.toString(entity);
            logger.info("res_code="+statusCode+";responseStr="+responseStr);
            EntityUtils.consume(entity);
            reponse.close();
            httpClient.close();
            hr.responseStr = responseStr;
            hr.statusCode = statusCode;
        }
        catch(Exception e){
            logger.info("Error in excuteHttpRequest:",e);
        }
        return hr;
    }

    public static class HttpResult{
        public int statusCode=-1;
        public String responseStr="";
    }

    public static List<NameValuePair> createNameValuePair(Map<String,String> params){
        List<NameValuePair> list = new ArrayList<>();
        if(params != null && !params.isEmpty()){
            Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String,String> entry = it.next();
                String key = entry.getKey();
                String value = entry.getValue();
                list.add(new BasicNameValuePair(key,value));
            }
        }
        return list;
    }
}
