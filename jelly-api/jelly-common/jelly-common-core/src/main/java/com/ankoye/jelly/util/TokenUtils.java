package com.ankoye.jelly.util;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.Resource;
import com.alibaba.fastjson.JSON;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ankoye@qq.com
 */
public class TokenUtils {
    // 证书
    private static final String JELLY_JKS = "jelly.jks";
    // 公钥证书
    private static final String PUBLIC_KEY = "public.key";
    // 公钥
    private static String publicKey;
    // 私钥
    private static RSAPrivateCrtKey privateKey;

    /**
     * 获取私钥和公钥
     */
    static  {
        org.springframework.core.io.ClassPathResource resource = new org.springframework.core.io.ClassPathResource(JELLY_JKS);
        // 加载证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "jelly.com".toCharArray());
        // 获取证书中的 秘钥
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("jelly", "jelly.com".toCharArray());
        // 获取私钥 - RSA
        privateKey = (RSAPrivateCrtKey) keyPair.getPrivate();
        // 获取公钥
        Resource pubResource = new ClassPathResource(PUBLIC_KEY);
        InputStreamReader inputStreamReader = new InputStreamReader(pubResource.getStream());
        BufferedReader br = new BufferedReader(inputStreamReader);
        publicKey = br.lines().collect(Collectors.joining("\n"));
    }

    /**
     * 创建Token
     */
    public static String create(Map<String, Object> claims) {
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(claims), new RsaSigner(privateKey));
        return jwt.getEncoded();
    }

    /**
     * 读取令牌数据
     */
    public static Map<String, Object> parse(String token){
        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));

        //获取Jwt原始内容
        String claims = jwt.getClaims();
        return JSON.parseObject(claims, Map.class);
    }



}