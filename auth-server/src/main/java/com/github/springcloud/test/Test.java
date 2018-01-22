package com.github.springcloud.test;

import com.github.springcloud.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.crypto.RsaProvider;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by ganzhen on 22/01/2018.
 */
public class Test {
    public static void main(String[] args){
        KeyPair keyPair = RsaProvider.generateKeyPair(1024);
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        try {

            String token = JwtHelper.generateTokenByPrivateKey("1",privateKey,1000);
            System.out.println(token);
            Jws<Claims> claims = JwtHelper.parserToken(token,publicKey);
            System.out.println(claims.getBody().getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
