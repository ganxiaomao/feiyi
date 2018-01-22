package com.github.springcloud.gatewayserver.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ganzhen on 22/01/2018.
 */
@FeignClient(value="auth-server")
public interface IAuthServerFeign {
    @RequestMapping(value="/jwt/token",method= RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestParam("userName") String username, @RequestParam("password") String password);

    @RequestMapping(value="/jwt/token",method= RequestMethod.GET)
    public ResponseEntity<?> validToken(@RequestParam("token") String token);

}
