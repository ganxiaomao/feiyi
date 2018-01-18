package com.github.springcloud.jwt;

import java.io.Serializable;

/**
 * jwt的response响应结构
 * Created by ganzhen on 17/01/2018.
 */
public class JwtAuthResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;

    public JwtAuthResponse(String token){
        this.token = token;
    }

    public JwtAuthResponse(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
