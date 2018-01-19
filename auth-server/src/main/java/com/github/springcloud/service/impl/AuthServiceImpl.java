package com.github.springcloud.service.impl;

import com.github.springcloud.entity.ClientEntity;
import com.github.springcloud.jwt.JwtAuthResponse;
import com.github.springcloud.jwt.JwtAuthResponseCode;
import com.github.springcloud.jwt.JwtTokenUtils;
import com.github.springcloud.mapper.ClientMapper;
import com.github.springcloud.service.AuthService;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ganzhen on 17/01/2018.
 */
@Service
public class AuthServiceImpl implements AuthService{
    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    ClientMapper clientMapper;

    @Override
    public JwtAuthResponse login(String username, String password) {
        logger.info("username="+username+";password="+password);
        JwtAuthResponse response = new JwtAuthResponse("defaultToken");

        String token = "";
        String code = "0000";
        String msg = "token生成成功";
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            Map<String,Object> params = new HashMap<>();
            params.put("userName",username);
            params.put("password",password);

            ClientEntity entity = clientMapper.selectOneByNameAndPassword(params);
            if(entity != null){
                try {
                    token = jwtTokenUtils.generateToken(entity.getId());

                } catch (Exception e) {
                    logger.error("Error in AuthServiceImpl.login",e);
                    code = JwtAuthResponseCode.response_code_token_generate_fail;//生成失败
                    msg = "token生成失败";
                }
            }
            else{
                code = JwtAuthResponseCode.response_code_cannt_find_data;//找不到数据
                msg = "数据查找不到";
            }
        }
        else{
            code = JwtAuthResponseCode.response_code_request_param_error;//参数传递有误
            msg = "请求参数有误";
        }
        response.setToken(token);
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    @Override
    public Map<String, Object> validateToken(String token) {
        boolean success = false;
        String code = "0001";
        String msg = "验证失败";
        try {
            String id = jwtTokenUtils.getIdFromToken(token);
            ClientEntity query = new ClientEntity();
            query.setId(id);
            ClientEntity entity = clientMapper.selectOne(query);
            if(entity != null){
                success = true;
                code = "0000";
                msg = "验证成功";
            }
        } catch (Exception e) {
            logger.error("Error in AuthServiceImpl.validateToken:",e);
        }
        return ImmutableMap.of("success",success,"code",code,"msg",msg);
    }
}
