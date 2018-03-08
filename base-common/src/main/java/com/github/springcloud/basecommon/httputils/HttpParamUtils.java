package com.github.springcloud.basecommon.httputils;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import org.apache.hc.client5.http.entity.mime.Header;
import org.apache.hc.client5.http.entity.mime.MinimalField;

import java.util.List;

/**
 * Created by ganzhen on 07/03/2018.
 */
public class HttpParamUtils {

    public static JSONArray convertList2JSONArray(List<Object> list){
        JSONArray ja = new JSONArray();
        if(list != null){
            for(Object obj : list){
                ja.add(obj);
            }
        }
        return ja;
    }

    public static Header arrangeHeader(String contentType, String contentEncoding){
        Header header = new Header();
        if(!Strings.isNullOrEmpty(contentType))
            header.addField(new MinimalField("Content-Type",contentType));
        if(!Strings.isNullOrEmpty(contentEncoding))
            header.addField(new MinimalField("Content-Encoding",contentEncoding));

        return header;
    }
}
