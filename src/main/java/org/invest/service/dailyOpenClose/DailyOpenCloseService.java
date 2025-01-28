package org.invest.service.dailyOpenClose;

import lombok.RequiredArgsConstructor;
import org.invest.client.PolygonClient;
import org.invest.dto.DailyDtoBetweenDates;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.entity.DailyOpenClose;

import org.invest.maper.DailyOpenCloseMapper;
import org.invest.repository.DailyOpenCloseRepository;
import org.invest.service.UsersDailyOpenCloseHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DailyOpenCloseService {
    private final DailyOpenCloseRepository dailyOpenCloseRepository;
    private final UsersDailyOpenCloseHistoryService historyService;
    private final DailyOpenCloseMapper dailyOpenCloseMapper;
    private final DailyOpenCloseServiceFasad dailyOpenCloseServiceFasad;

    public List<DailyOpenCloseDto> getDailyOpenClosesBetweenDates(DailyDtoBetweenDates betweenDates, Long userId) {
        dailyOpenCloseServiceFasad.fillDatabaseWithMissingValues(betweenDates);
        fillHistoryWithMissingValues(betweenDates, userId);

        List<DailyOpenClose> dailyOpenCloseList = historyService.getDailyOpenClose(betweenDates,userId);

        return getDailyOpenCloseDtoList(dailyOpenCloseList);
    }

    public List<DailyOpenCloseDto> getDailyOpenClosesByTicker(String ticker, Long userId) {
        List<DailyOpenClose> dailyOpenCloseList = historyService.getUserDailyOpenCloseHistory(userId,ticker);

        return getDailyOpenCloseDtoList(dailyOpenCloseList);
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

    private List<DailyOpenCloseDto> getDailyOpenCloseDtoList(List<DailyOpenClose> dailyOpenCloseList){
        List<DailyOpenCloseDto> dailyOpenCloseDtoList = dailyOpenCloseMapper.toDtoList(dailyOpenCloseList);
        Collections.sort(dailyOpenCloseDtoList);
        return dailyOpenCloseDtoList;
    }

}
