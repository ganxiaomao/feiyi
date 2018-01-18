package com.github.springcloud.jwt;

import java.io.Serializable;

/**
 * jwt的request请求结构
 * Created by ganzhen on 17/01/2018.
 */
public class JwtAuthRequest implements Serializable{
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    public JwtAuthRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public JwtAuthRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
