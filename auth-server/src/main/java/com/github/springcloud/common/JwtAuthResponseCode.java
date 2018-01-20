package com.github.springcloud.common;

/**
 * auth-server的返回code
 * Created by ganzhen on 19/01/2018.
 */
public class JwtAuthResponseCode {
    //返回码：token生成失败
    public static final String response_code_token_generate_fail = "0001";
    //返回码：数据库中找不到数据
    public static final String response_code_cannt_find_data = "0002";
    //返回码：请求参数有误
    public static final String response_code_request_param_error = "0003";
}
