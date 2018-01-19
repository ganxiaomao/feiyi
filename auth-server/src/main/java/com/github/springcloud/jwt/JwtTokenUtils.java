package com.github.springcloud.jwt;

import com.github.springcloud.RsaConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return JwtHelper.generateTokenByPrivateKey(id,rsaConfiguration.getPriKey(),JwtConfig.token_exp_interval);
    }

    /**
     * 解析token，从中获取原id
     * @param token
     * @return
     * @throws Exception
     */
    public String getIdFromToken(String token) throws Exception {
        String id = "";
        Jws<Claims> claims = JwtHelper.parserToken(token, rsaConfiguration.getPubKey());
        Claims body = claims.getBody();
        id = body.getSubject();
        return id;
    }
}
