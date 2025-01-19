package org.invest.service;

import lombok.RequiredArgsConstructor;
import org.invest.client.PolygonClient;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.dto.DailyOpenCloseDtoBetweenDates;
import org.invest.dto.TickerDto;
import org.invest.entity.DailyOpenClose;
import org.invest.entity.User;
import org.invest.repository.DailyOpenCloseRepository;
import org.invest.repository.UserRepository;
import org.invest.repository.UsersDailyOpenCloseHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyOpenCloseService {
    private final DailyOpenCloseRepository dailyOpenCloseRepository;
    private final UsersDailyOpenCloseHistoryService historyService;
    private final PolygonClient polygonClient;

    public List<DailyOpenCloseDto> getDailyOpenClosesBetweenDates(DailyOpenCloseDtoBetweenDates betweenDates,
                                                                  Long userId) {

        List<DailyOpenClose> usersDailyOpenCloseList = historyService.getDailyOpenClose(betweenDates, userId);

        addMissingDays(usersDailyOpenCloseList,betweenDates);
        historyService.saveListDailyOpenCloseHistory(usersDailyOpenCloseList,userId);

        return mapDailyOpenCloseList(usersDailyOpenCloseList);
    }

    public List<DailyOpenCloseDto> getDailyOpenClosesByTicker(TickerDto tickerDto, Long userId) {

        List<DailyOpenClose> usersDailyOpenCloseList =
                historyService.getUserDailyOpenCloseHistory(userId,tickerDto.getTicker());

        return mapDailyOpenCloseList(usersDailyOpenCloseList);
    }

    private void addMissingDays(List<DailyOpenClose> usersDailyOpenCloseList,
                                      DailyOpenCloseDtoBetweenDates betweenDates){

        Collections.sort(usersDailyOpenCloseList);
        LocalDate startDate = betweenDates.getOpenDate();
        LocalDate finishDate = betweenDates.getCloseDate();
        int count = 0;

        for (int i = startDate.getDayOfYear()-1; i < finishDate.getDayOfYear(); i++) {

            startDate = startDate.plusDays(1);

            DailyOpenClose dailyOpenClose = usersDailyOpenCloseList.get(count);

            if(dailyOpenClose.getFrom().isEqual(startDate)){
                count++;
                continue;
            }

            DailyOpenClose newDailyOpenClose =
                    dailyOpenCloseRepository.findBySymbolAndDateFrom(betweenDates.getTicker(), startDate);


            if(newDailyOpenClose != null){
                usersDailyOpenCloseList.add(newDailyOpenClose);
                Collections.sort(usersDailyOpenCloseList);
                count++;
                continue;
            }

            usersDailyOpenCloseList.add(getDailyOpenCloseInFeignClient(betweenDates.getTicker(),startDate));
            Collections.sort(usersDailyOpenCloseList);
            count++;
        }
    }

    private DailyOpenClose getDailyOpenCloseInFeignClient(String ticker,LocalDate date){
        DailyOpenCloseDto dailyOpenClose = polygonClient.getPolygon(ticker,date);
        DailyOpenClose dailyOpenCloseResult =DailyOpenClose.builder()
                                                            .status(dailyOpenClose.getStatus())
                                                            .from(dailyOpenClose.getFrom())
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

    private List<DailyOpenCloseDto> mapDailyOpenCloseList(List<DailyOpenClose> dailyOpenCloseList) {
        List<DailyOpenCloseDto> dailyOpenCloseDtoList = new ArrayList<>();

        for(DailyOpenClose dailyOpenClose : dailyOpenCloseList){
            dailyOpenCloseDtoList.add(DailyOpenCloseDto.builder()
                                                        .status(dailyOpenClose.getStatus())
                                                        .from(dailyOpenClose.getFrom())
                                                        .symbol(dailyOpenClose.getSymbol())
                                                        .open(dailyOpenClose.getOpen())
                                                        .high(dailyOpenClose.getHigh())
                                                        .low(dailyOpenClose.getLow())
                                                        .close(dailyOpenClose.getClose())
                                                        .volume(dailyOpenClose.getVolume())
                                                        .afterHours(dailyOpenClose.getAfterHours())
                                                        .preMarket(dailyOpenClose.getPreMarket())
                                                        .build()
            );
        }
        return dailyOpenCloseDtoList;
    }

}
