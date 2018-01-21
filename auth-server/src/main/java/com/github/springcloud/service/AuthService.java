package com.github.springcloud.service;

import com.github.springcloud.jwt.JwtAuthResponse;

import java.util.Map;

/**
 * Created by ganzhen on 17/01/2018.
 */
public interface AuthService {
    public JwtAuthResponse login(String username, String password);

    public JwtAuthResponse validateToken(String token);
}
