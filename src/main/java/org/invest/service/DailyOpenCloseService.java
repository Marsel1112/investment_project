package org.invest.service;

import lombok.RequiredArgsConstructor;
import org.invest.client.PolygonClient;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.dto.DailyDtoBetweenDates;
import org.invest.entity.DailyOpenClose;
import org.invest.exception.NotFoundException;
import org.invest.maper.DailyOpenCloseMapper;
import org.invest.repository.DailyOpenCloseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyOpenCloseService {
    private final DailyOpenCloseRepository dailyOpenCloseRepository;
    private final UsersDailyOpenCloseHistoryService historyService;
    private final PolygonClient polygonClient;
    private final DailyOpenCloseMapper dailyOpenCloseMapper;

    public List<DailyOpenCloseDto> getDailyOpenClosesBetweenDates(DailyDtoBetweenDates betweenDates, Long userId) {
        fillDatabaseWithMissingValues(betweenDates);
        fillHistoryWithMissingValues(betweenDates, userId);

        List<DailyOpenClose> dailyOpenCloseList = historyService.getDailyOpenClose(betweenDates,userId);

        List<DailyOpenCloseDto> dailyOpenCloseDtoList = dailyOpenCloseMapper.toDtoList(dailyOpenCloseList);

        Collections.sort(dailyOpenCloseDtoList);

        return dailyOpenCloseDtoList;
    }

    public List<DailyOpenCloseDto> getDailyOpenClosesByTicker(String ticker, Long userId) {

        List<DailyOpenClose> dailyOpenCloseList =
                historyService.getUserDailyOpenCloseHistory(userId,ticker);

        List<DailyOpenCloseDto> dailyOpenCloseDtoList = dailyOpenCloseMapper.toDtoList(dailyOpenCloseList);

        Collections.sort(dailyOpenCloseDtoList);

        return dailyOpenCloseDtoList;
    }

    private DailyOpenClose getDailyOpenCloseInFeignClient(String ticker,LocalDate date){
        DailyOpenCloseDto dailyOpenClose = polygonClient.getPolygon(ticker,date.toString());
        DailyOpenClose dailyOpenCloseResult =DailyOpenClose.builder()
                                                            .status(dailyOpenClose.getStatus())
                                                            .dateFrom(dailyOpenClose.getFrom())
                                                            .symbol(dailyOpenClose.getSymbol())
                                                            .open(dailyOpenClose.getOpen())
                                                            .high(dailyOpenClose.getHigh())
                                                            .low(dailyOpenClose.getLow())
                                                            .close(dailyOpenClose.getClose())
                                                            .volume(dailyOpenClose.getVolume())
                                                            .afterHours(dailyOpenClose.getAfterHours())
                                                            .preMarket(dailyOpenClose.getPreMarket())
                                                            .build();

        return dailyOpenCloseRepository.save(dailyOpenCloseResult);
    }

    private void fillDatabaseWithMissingValues(DailyDtoBetweenDates betweenDates){
        String ticker = betweenDates.getTicker();
        LocalDate startDate = betweenDates.getOpenDate();

        for (int i = 0; i < betweenDates.getCountDay(); i++) {
            LocalDate givenDay = startDate.plusDays(i);
            if(!dailyOpenCloseRepository.existsBySymbolAndDateFrom(ticker,givenDay)){
                try {
                    getDailyOpenCloseInFeignClient(ticker, givenDay);
                } catch (NotFoundException notFoundException) {
                    saveNotFoundDailyOpenClose(ticker, givenDay);
                }
            }
        }
    }

    private void saveNotFoundDailyOpenClose(String ticker, LocalDate dateFrom){
        dailyOpenCloseRepository.save(DailyOpenClose.builder()
                .symbol(ticker)
                .dateFrom(dateFrom)
                .status("Отсутсвует")
                .build());
    }

    private void fillHistoryWithMissingValues(DailyDtoBetweenDates betweenDates, Long userId){
        String ticker = betweenDates.getTicker();
        LocalDate startDate = betweenDates.getOpenDate();

        for (int i = 0; i < betweenDates.getCountDay(); i++) {
            LocalDate givenDay = startDate.plusDays(i);

            if(!historyService.existsUserDailyOpenCloseHistory(userId,ticker,givenDay)){
                DailyOpenClose dailyOpenClose = dailyOpenCloseRepository.findBySymbolAndDateFrom(ticker,givenDay);
                historyService.saveUserDailyOpenCloseHistory(userId,dailyOpenClose);
            }
        }
    }

}
