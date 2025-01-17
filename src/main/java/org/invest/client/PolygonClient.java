package org.invest.client;

import org.invest.config.Feign.FeignConfig;
import org.invest.dto.DailyOpenCloseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "polygon.io",configuration = FeignConfig.class,url = "https://api.polygon.io")
public interface PolygonClient {
    @GetMapping("/v1/open-close/{ticker}/{date}")
    public DailyOpenCloseDto getPolygon(@PathVariable String ticker, @PathVariable String date);

}
