package com.github.springcloud;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ganzhen on 19/01/2018.
 */
@Configuration
@Data
public class RsaConfiguration {
    private String secret;
    private byte[] pubKey;//公钥
    private byte[] priKey;//私钥
}
