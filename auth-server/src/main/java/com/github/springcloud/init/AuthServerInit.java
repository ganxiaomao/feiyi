package com.github.springcloud.init;

import com.github.springcloud.RsaConfiguration;
import com.github.springcloud.jwt.RsaHelper;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.security.KeyPair;
import java.util.Map;

/**
 * Created by ganzhen on 19/01/2018.
 */
@Configuration
public class AuthServerInit implements CommandLineRunner{
    Logger logger = LoggerFactory.getLogger(AuthServerInit.class);
    @Autowired
    private RsaConfiguration rsaConfiguration;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("初始化RSA配置信息");
        rsaConfiguration.setSecret("ganzhen8283");
        KeyPair keyPair = RsaProvider.generateKeyPair(1024);//RsaHelper.generateKey(rsaConfiguration.getSecret());

        rsaConfiguration.setPrivateKey(keyPair.getPrivate());
        rsaConfiguration.setPublicKey(keyPair.getPublic());
        //logger.info("pub:"+new String(rsaConfiguration.getPubKey())+";pri:"+new String(rsaConfiguration.getPriKey()));
    }
}
