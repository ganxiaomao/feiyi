package com.github.springcloud.controller;

import com.github.springcloud.entity.ClientEntity;
import com.github.springcloud.jwt.JwtAuthRequest;
import com.github.springcloud.jwt.JwtAuthResponse;
import com.github.springcloud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Request;

/**
 * Created by ganzhen on 17/01/2018.
 */
@RestController
@RequestMapping("jwt")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value="token",method= RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(String username, String password){
        ClientEntity entity = new ClientEntity();
        JwtAuthRequest request = new JwtAuthRequest(username, password);
        if(request != null){
            JwtAuthResponse response = authService.login(request.getUsername(), request.getPassword());
            if(response != null){
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.ok(null);
    }
}
