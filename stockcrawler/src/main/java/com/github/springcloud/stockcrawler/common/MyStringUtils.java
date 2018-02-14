package com.github.springcloud.stockcrawler.common;

import com.google.common.base.Strings;

/**
 * Created by ganzhen on 14/02/2018.
 */
public class MyStringUtils {

    public static String replaceAllByStr(String origin, String regex, String str){
        String res = "";
        if(!Strings.isNullOrEmpty(origin) && !Strings.isNullOrEmpty(str)){
            res = origin.replaceAll(regex,str);
        }
        return res;
    }
}
