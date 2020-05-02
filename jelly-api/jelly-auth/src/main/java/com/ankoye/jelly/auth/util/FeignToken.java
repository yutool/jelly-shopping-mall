package com.ankoye.jelly.auth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

public class FeignToken {
    private static final String RESOURCE = "jelly.jks";
    private static RSAPrivateCrtKey privateKey;

    static  {
        ClassPathResource resource = new ClassPathResource(RESOURCE);

        // 加载证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "jelly.com".toCharArray());

        // 获取证书中的 秘钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jelly", "jelly.com".toCharArray());

        // 获取私钥 - RSA
        privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();

    }

    public static String create() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "feign");
        claims.put("authorities", new String[] {"FEIGN"});

        Jwt jwt = JwtHelper.encode(JSON.toJSONString(claims), new RsaSigner(privateKey));
        return jwt.getEncoded();
    }


//    public static void main(String[] args) {
//        System.out.println(create());
//    }
}
