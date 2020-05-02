package com.ankoye.jelly;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class TestToken {
    @Test
    public void testToken() {
        String key = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqucMPQqpIyuiKL9I4BgCKXtW6IrnuK6DHHA+wdNlOhJAp5OgyyTm25SafkWFXsamzu6ja/EriPV0ub7P4ZtcwNdi3jqhBhyVgzqXpTk8UL0YffAQZUfzlB+7LnWS5ysTFSQAHhdDXCl2jxFNcYnhLpfDYWF1sb39KO2CyKd/UYclG0/27tS4EWdqcrPgQ264YbDH/m8J9CsKgoP0pGJsrvbuEt+io5IgSkBlZ+9fepfUN9d9+x5QNzX0jRU35qqvtHcIBxBc7qxACEjHG5MSUSROJoaz6WZTyQTLauCCSlVlutJ0McXz4moUyCCGlZltyrExGs7o1EsMTIX7X4ijDQIDAQAB-----END PUBLIC KEY-----";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwiaWQiOiIxIiwiZXhwIjoxNTg4NDM4MTU4LCJhY2NvdW50IjoidGVzdEBxcS5jb20iLCJhdXRob3JpdGllcyI6WyJVU0VSIl0sImp0aSI6IjczYWIyNDkwLTJiYTgtNDYxYS1hNTAxLTdjNzBkNWRmZmI5MyIsImNsaWVudF9pZCI6ImplbGx5LXNlcnZlIiwidXNlcm5hbWUiOiJ0ZXN0In0.iJR-W45_Sy0J31xu8UeXRy7d6t3KH-jSlX0-MgFmem1zHurfiK-Xf3Ktpa717aU3tyYijBrHaOuBN64A3f02ODmxxHrFExxpJFnFK-B-LG4rdKVNVmxSOIZNJKrgNyTqYKmTcip3wuPb4zX1d9kHDvG61FLRfNY6A45vj5wOnwiZiC4gy4KjRHawOvKagb6izITNVgjbwzST6-hIfeQyM1cD5MhrNsmo1yPpdOCV8oHVMfUf-sFtZ8yp3efBMYBQ9D2IMMsLVrWGL6pg4MdDD_5KYY5OP1HOylzNm3fcIsxvUfCOrF2-tk0QOSgkKD7IZrlpDrH86jfrg3QNc8VAcA";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(key));
        System.out.println(jwt.getClaims());
    }
}
