package org.invest.service.dailyOpenClose;

import lombok.RequiredArgsConstructor;
import org.invest.client.PolygonClient;
import org.invest.dto.DailyDtoBetweenDates;
import org.invest.dto.DailyOpenCloseDto;
import org.invest.entity.DailyOpenClose;
import org.invest.exception.NotFoundException;
import org.invest.maper.DailyOpenCloseMapper;
import org.invest.repository.DailyOpenCloseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyOpenCloseServiceFasad {
    private final PolygonClient polygonClient;
    private final DailyOpenCloseRepository dailyOpenCloseRepository;
    private final DailyOpenCloseMapper dailyOpenCloseMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void fillDatabaseWithMissingValues(DailyDtoBetweenDates betweenDates) {
        String ticker = betweenDates.getTicker();
        LocalDate startDate = betweenDates.getOpenDate();

        for (int i = 0; i < betweenDates.getCountDay(); i++) {
            LocalDate givenDay = startDate.plusDays(i);

            if (!dailyOpenCloseRepository.existsBySymbolAndDateFrom(ticker, givenDay)) {
                try {
                    getDailyOpenCloseInFeignClient(ticker, givenDay);
                } catch (NotFoundException notFoundException) {
                    try {
                        saveNotFoundDailyOpenClose(ticker, givenDay);
                    } catch (DataIntegrityViolationException ignored) {

                    }
                }
            }
        }
    }

    private void getDailyOpenCloseInFeignClient(String ticker,LocalDate date){
        DailyOpenCloseDto dailyOpenClose = polygonClient.getPolygon(ticker,date.toString());
        DailyOpenClose dailyOpenCloseResult = dailyOpenCloseMapper.toEntity(dailyOpenClose);
        dailyOpenCloseRepository.save(dailyOpenCloseResult);
    }

    private void saveNotFoundDailyOpenClose(String ticker, LocalDate dateFrom){
        dailyOpenCloseRepository.save(DailyOpenClose.builder()
                .symbol(ticker)
                .dateFrom(dateFrom)
                .status("Отсутсвует")
                .build());
    }
}
