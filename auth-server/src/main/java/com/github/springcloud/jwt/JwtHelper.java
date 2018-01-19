package com.github.springcloud.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

/**
 * Created by ganzhen on 19/01/2018.
 */
public class JwtHelper {
    private static RsaHelper rsaHelper;

    public static String generateTokenByPrivateKey(String id, byte[] rsaPrivateKey, int expSeconds) throws Exception {
        String token = "";
        token = Jwts.builder()
                .setSubject(id)
                .setExpiration(DateTime.now().plusSeconds(expSeconds).toDate())
                .signWith(SignatureAlgorithm.HS256,rsaHelper.getPrivateKey(rsaPrivateKey))
                .compact();
        return token;
    }

    public static Jws<Claims> parserToken(String token, byte[] rsaPublicKey) throws Exception {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(rsaHelper.getPublicKey(rsaPublicKey))
                .parseClaimsJws(token);
        return claims;
    }
}
