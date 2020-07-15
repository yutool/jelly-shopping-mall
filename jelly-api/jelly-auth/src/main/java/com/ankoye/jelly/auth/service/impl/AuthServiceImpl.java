package com.ankoye.jelly.auth.service.impl;

import com.ankoye.jelly.auth.model.AuthToken;
import com.ankoye.jelly.auth.service.AuthService;
import com.ankoye.jelly.base.result.ResultCode;
import com.ankoye.jelly.web.exception.CastException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @author ankoye@qq.com
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public AuthToken login(String username, String password, String grantType) {
        // 1.调用oauth2提供的授权接口
        ServiceInstance serviceInstance = loadBalancerClient.choose("jelly-auth");
        URI uri = serviceInstance.getUri();
        String url = uri + "/oauth/token";
        // 2.封装请求体
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("username", username);
        body.add("password", password);
        // 3.封装请求头
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", this.getHttpBasic(clientId, clientSecret));
        // 4.封装请求信息
        HttpEntity<MultiValueMap<String,String>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401){
                    //super.handleError(response);
                    throw new RuntimeException("请求错误");
                }
            }
        });
        // 5.调用请求
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        Map map = responseEntity.getBody();
        if (map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null){
            // 申请令牌失败
            CastException.cast(ResultCode.USER_LOGIN_ERROR);
        }
        // 6.封装结果数据
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) map.get("access_token"));
        authToken.setRefreshToken((String) map.get("refresh_token"));
        authToken.setJti((String)map.get("jti"));

        return authToken;
    }

    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic " + new String(encode);
    }
}
