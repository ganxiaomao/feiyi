package com.github.springcloud.service.impl;

import com.github.springcloud.entity.ClientEntity;
import com.github.springcloud.jwt.JwtAuthResponse;
import com.github.springcloud.mapper.ClientMapper;
import com.github.springcloud.service.AuthService;
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
    ClientMapper clientMapper;

    @Override
    public JwtAuthResponse login(String username, String password) {
        logger.info("username="+username+";password="+password);
        JwtAuthResponse response = new JwtAuthResponse("defaultToken");
        if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
            Map<String,Object> params = new HashMap<>();
            params.put("userName",username);
            params.put("password",password);

            ClientEntity entity = clientMapper.selectOneByNameAndPassword(params);
            if(entity != null){
                Date now = new Date();
                entity.getExpireTime();
                response.setToken("123456Token");
            }
        }

        return response;
    }
}
