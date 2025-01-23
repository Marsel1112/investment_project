package org.invest.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@RequiredArgsConstructor
public class FeignConfig  {
    @Value("${token.auth.bearer}")
    private  String token;

    @Bean
    public RequestInterceptor  requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
