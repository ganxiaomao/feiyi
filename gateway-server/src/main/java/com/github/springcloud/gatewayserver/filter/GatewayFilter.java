package com.github.springcloud.gatewayserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.github.springcloud.gatewayserver.feign.IAuthServerFeign;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ganzhen on 22/01/2018.
 */
@Component
@Slf4j
public class GatewayFilter extends ZuulFilter{
    Logger logger = LoggerFactory.getLogger(GatewayFilter.class);
    @Autowired
    private IAuthServerFeign iAuthServerFeign;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String requestUri = request.getRequestURI();
        logger.info("进入路由的请求地址："+requestUri);
        printRequestParams(request);
        if(requestUri.endsWith("token"))//不进行拦截，获取token的api
            return null;
        final String method = request.getMethod();
        //获取请求中得token
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            ctx.setSendZuulResponse(false);//不对其进行路由
            ctx.setResponseStatusCode(401);
            ctx.setResponseBody("必须有token参数");//返回错误内容
            ctx.set("success",false);
            return null;
        }
        request.getParameterMap();
        ResponseEntity<?> entity = iAuthServerFeign.validToken(token);
        Object body = entity.getBody();
        LinkedHashMap<String,String> lm = (LinkedHashMap<String, String>) body;
        if(body != null){
            String code = lm.get("code");
            if(StringUtils.isNotEmpty(code) && code.equals("0000")){
                ctx.setSendZuulResponse(true);//不对其进行路由
                ctx.setResponseStatusCode(200);
                ctx.setResponseBody(lm.get("msg"));//返回错误内容
                ctx.set("success",true);
            }
            else {
                ctx.setSendZuulResponse(false);//不对其进行路由
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(lm.get("msg"));//返回错误内容
                ctx.set("success",false);
            }
        }

        return null;
    }

    private void printRequestParams(HttpServletRequest request){
        Map<String,String[]> paramsMap = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> it = paramsMap.entrySet().iterator();
        String print = "";
        while(it.hasNext()){
            Map.Entry<String,String[]> entry = it.next();
            String paramName = entry.getKey();
            String[] paramValue = entry.getValue();
            print += paramName+"="+Arrays.asList(paramValue).toString();
        }
        logger.info("进入路由的请求参数："+print);
    }
}
