package com.github.springcloud.service;

import com.github.springcloud.jwt.JwtAuthResponse;

/**
 * Created by ganzhen on 17/01/2018.
 */
public interface AuthService {
    JwtAuthResponse login(String username, String password);
}
