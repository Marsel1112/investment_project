package org.invest.config.Feign;

import feign.codec.ErrorDecoder;
import org.invest.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder csomErrorDecoder(){
        return new CustomErrorDecoder();
    }
}

