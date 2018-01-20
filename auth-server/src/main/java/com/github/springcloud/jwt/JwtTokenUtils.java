package com.github.springcloud.jwt;

import com.github.springcloud.RsaConfiguration;
import com.github.springcloud.configuration.JwtConfiguration;
import com.github.springcloud.vo.AuthInfo;
import com.google.common.collect.ImmutableMap;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Created by ganzhen on 19/01/2018.
 */
@Component
public class JwtTokenUtils {

    @Autowired
    private RsaConfiguration rsaConfiguration;

    /**
     * 依据id生成token
     * @param id
     * @return
     * @throws Exception
     */
    public String generateToken(String id) throws Exception {
        return JwtHelper.generateTokenByPrivateKey(id,rsaConfiguration.getPriKey(), JwtConfiguration.token_exp_interval);
    }

    /**
     * 解析token，从中获取原id,expDate
     * @param token
     * @return
     * @throws Exception
     */
    public AuthInfo getInfoFromToken(String token) throws Exception {
        String id = "";
        Jws<Claims> claims = JwtHelper.parserToken(token, rsaConfiguration.getPubKey());
        Claims body = claims.getBody();
        id = body.getSubject();
        Date expDate = body.getExpiration();
        return new AuthInfo(id,expDate);
    }
}
