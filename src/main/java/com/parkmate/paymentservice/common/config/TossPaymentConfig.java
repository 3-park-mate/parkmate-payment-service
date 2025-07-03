package com.parkmate.paymentservice.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

@Getter
@Configuration
public class TossPaymentConfig {

//    @Value("${payment.toss.client_key}")
    private String clientKey = "test_ck_yZqmkKeP8gJzXnZBRqNYrbQRxB9l";

    //    @Value("${payment.toss.secret_key}")
    private String secretKey = "test_sk_4yKeq5bgrpL5ExxbqG2ArGX0lzW6";

//    @Value("${payment.toss.base_url}")
    private String baseUrl = "https://api.tosspayments.com/v1/payments";

//    @Value("${payment.toss.success_url}")
    private String successUrl = "http://localhost:63342/payment-service/src/main/resources/templates/success.html";

//    @Value("${payment.toss.fail_url}")
    private String failUrl = "http://localhost:63342/payment-service/src/main/resources/templates/fail.html";

    @Bean
    public HttpHeaders getHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        String credentials = secretKey + ":";
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        httpHeaders.set("Authorization", "Basic " + encodedCredentials);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}
