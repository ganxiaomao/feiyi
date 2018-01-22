package com.github.springcloud.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ganzhen on 19/01/2018.
 */
public class JwtHelper {
    private static RsaHelper rsaHelper = new RsaHelper();

    public static String generateTokenByPrivateKey(String id, PrivateKey rsaPrivateKey, int expSeconds) throws Exception {
        String token = "";
        token = Jwts.builder()
                .setSubject(id)
                .setExpiration(DateTime.now().plusSeconds(expSeconds).toDate())
                .signWith(SignatureAlgorithm.RS256,rsaPrivateKey)//很多例子用的都是HS256，但这个会一直提醒你用的privatekey不是一个secretkey，所以此处改成RS256
                .compact();
        return token;
    }

    public static Jws<Claims> parserToken(String token, PublicKey rsaPublicKey) throws Exception {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(rsaPublicKey)
                .parseClaimsJws(token);
        return claims;
    }
}
