package org.invest.config.Feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.invest.exeption.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder csomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}

