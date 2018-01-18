package com.github.springcloud.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by ganzhen on 18/01/2018.
 */
public class JwtUtils {
    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static String createToken(String id,String code){
        String token = "";
        try{
            SignatureAlgorithm sa = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            JwtBuilder builder = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setSubject(code)
                    .setIssuer("");
            Date expDate = new Date(nowMillis+JwtConfig.token_exp_interval);

            builder.setExpiration(expDate);

            token = builder.compact();

        }
        catch(Exception e){
            logger.info("",e);
        }
        return token;
    }


}
