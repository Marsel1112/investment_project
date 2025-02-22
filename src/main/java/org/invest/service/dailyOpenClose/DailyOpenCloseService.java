package org.invest.service.dailyOpenClose;

import lombok.RequiredArgsConstructor;
import org.invest.dto.DailyDtoBetweenDates;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.entity.DailyOpenClose;

import org.invest.exception.NotFoundException;
import org.invest.maper.DailyOpenCloseMapper;
import org.invest.repository.DailyOpenCloseRepository;
import org.invest.service.UsersDailyOpenCloseHistoryService;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final DailyOpenCloseMapper dailyOpenCloseMapper;
    private final DailyOpenCloseServiceFacade dailyOpenCloseServiceFacade;

    public List<DailyOpenCloseDto> getDailyOpenClosesBetweenDates(DailyDtoBetweenDates betweenDates, Long userId) {
        fillDatabaseWithMissingValues(betweenDates);
        fillHistoryWithMissingValues(betweenDates, userId);

        List<DailyOpenClose> dailyOpenCloseList = historyService.getDailyOpenClose(betweenDates,userId);

        return getDailyOpenCloseDtoList(dailyOpenCloseList);
    }

    public List<DailyOpenCloseDto> getDailyOpenClosesByTicker(String ticker, Long userId) {
        List<DailyOpenClose> dailyOpenCloseList = historyService.getUserDailyOpenCloseHistory(userId,ticker);
        return getDailyOpenCloseDtoList(dailyOpenCloseList);
    }

    private void fillDatabaseWithMissingValues(DailyDtoBetweenDates betweenDates) {
        String ticker = betweenDates.getTicker();
        LocalDate startDate = betweenDates.getOpenDate();
        List<DailyOpenClose> dailyOpenCloseList = new ArrayList<>();
        for (int i = 0; i < betweenDates.getCountDay(); i++) {
            LocalDate givenDay = startDate.plusDays(i);
            dailyOpenCloseList.add(getMissingDailyOpenClose(ticker, givenDay));
        }

        dailyOpenCloseServiceFacade.saveDailyOpenClose(dailyOpenCloseList);
    }

    private DailyOpenClose getMissingDailyOpenClose(String ticker,  LocalDate givenDay){
        DailyOpenClose dailyOpenClose = null;
        if (!dailyOpenCloseRepository.existsBySymbolAndDateFrom(ticker, givenDay)) {
            try {
                dailyOpenClose = dailyOpenCloseServiceFacade.getDailyOpenCloseInFeignClient(ticker, givenDay);
            } catch (NotFoundException notFoundException) {
                try {
                    dailyOpenClose = dailyOpenCloseServiceFacade.getNotFoundDailyOpenClose(ticker, givenDay);
                } catch (DataIntegrityViolationException ignored) {}
            }
        }
        return dailyOpenClose;
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
