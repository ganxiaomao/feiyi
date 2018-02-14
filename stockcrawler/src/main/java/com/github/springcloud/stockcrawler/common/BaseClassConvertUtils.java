package com.github.springcloud.stockcrawler.common;

import com.google.common.base.Strings;

import java.math.BigDecimal;

/**
 * 基本数据类型转换工具
 * Created by ganzhen on 14/02/2018.
 */
public class BaseClassConvertUtils {

    /**
     * 字符串转换成BigDecimal
     * @param str
     * @return 出错返回null
     */
    public static BigDecimal string2BigDecimal(String str){
        BigDecimal res = null;
        try{
            res = new BigDecimal(str);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
