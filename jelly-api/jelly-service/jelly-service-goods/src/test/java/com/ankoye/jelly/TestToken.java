package com.ankoye.jelly;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class TestToken {
    @Test
    public void testToken() {
        String key = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqucMPQqpIyuiKL9I4BgCKXtW6IrnuK6DHHA+wdNlOhJAp5OgyyTm25SafkWFXsamzu6ja/EriPV0ub7P4ZtcwNdi3jqhBhyVgzqXpTk8UL0YffAQZUfzlB+7LnWS5ysTFSQAHhdDXCl2jxFNcYnhLpfDYWF1sb39KO2CyKd/UYclG0/27tS4EWdqcrPgQ264YbDH/m8J9CsKgoP0pGJsrvbuEt+io5IgSkBlZ+9fepfUN9d9+x5QNzX0jRU35qqvtHcIBxBc7qxACEjHG5MSUSROJoaz6WZTyQTLauCCSlVlutJ0McXz4moUyCCGlZltyrExGs7o1EsMTIX7X4ijDQIDAQAB-----END PUBLIC KEY-----";
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU4ODQwMTg4MCwiYXV0aG9yaXRpZXMiOlsiVVNFUiJdLCJqdGkiOiJiMzk2ZjI5ZC1hMjRkLTQ3NzgtOGIyNy04MmMxNWRkYmQ2YjAiLCJjbGllbnRfaWQiOiJqZWxseS1zZXJ2ZSIsInVzZXJuYW1lIjoidGVzdCJ9.SPOdHVmRMqngsxuRwJpTCxzFImv831wgQJOwZB4Aq57yTdJGcCIo2roeW6X1UuWZuk6flgN4BiUIGE7ngGqrkqqEVBt2kkxGYDGrve_xN4juAMnNptBEUHXjROVGn3MUro-qY1hQs2c0BmAQjVSAMvof5UwSqwIgzpIp8NB6pJULVOsRyNpGF5NmqR5JwZ_ZK3GhvC6RQRSy-sQayBlaJPEJuNcXOWJ-GCYP0Is6sJR8DCwRBnUxdQjawuej7xaKayahEyeYD33sb79YoXm4bTah5rkkFuG6f24QDMBmx8M15cdxPXQZIla0Ad7AGBH3TUxHQM_25odeHNg9yu2Plw";
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(key));
        System.out.println(jwt.getClaims());
    }
}
