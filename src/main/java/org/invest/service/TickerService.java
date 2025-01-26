package org.invest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.invest.client.PolygonClient;
import org.invest.dto.TickerResponse;
import org.invest.entity.Ticker;
import org.invest.exception.TickerNotFoundException;
import org.invest.maper.TickerMapper;
import org.invest.repository.TickerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TickerService {
    private final TickerRepository tickerRepository;
    private final PolygonClient polygonClient;
    private final TickerMapper tickerMapper;

    public void validateTicker(String tickerValue) {
        Ticker ticker = tickerRepository.findByTicker(tickerValue);

        if (ticker == null)
            loadTickers(tickerValue);
    }

    private void loadTickers(String tickerValue){
        try {
            TickerResponse response = polygonClient.getTickerInfo(tickerValue);
            Ticker ticker = tickerMapper.toTicker(response.getResults());
            tickerRepository.save(ticker);

        }catch (Exception e){
            throw new TickerNotFoundException(tickerValue);
        }
    }

}
