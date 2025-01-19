package org.invest.client;

import org.invest.config.Feign.FeignConfig;
import org.invest.dto.DailyOpenCloseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(value = "polygon.io",configuration = FeignConfig.class,url = "https://api.polygon.io")
public interface PolygonClient {
    @GetMapping("/v1/open-close/open-close/{ticker}/{date}")
    public DailyOpenCloseDto getPolygon(@PathVariable String ticker, @PathVariable LocalDate date);

}
