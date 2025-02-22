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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyOpenCloseServiceFacade {
    private final PolygonClient polygonClient;
    private final DailyOpenCloseRepository dailyOpenCloseRepository;
    private final DailyOpenCloseMapper dailyOpenCloseMapper;

    protected DailyOpenClose getDailyOpenCloseInFeignClient(String ticker,LocalDate date){
        DailyOpenCloseDto dailyOpenClose = polygonClient.getPolygon(ticker,date.toString());
        return dailyOpenCloseMapper.toEntity(dailyOpenClose);
    }

    protected DailyOpenClose getNotFoundDailyOpenClose(String ticker, LocalDate dateFrom){
        return DailyOpenClose.builder()
                .symbol(ticker)
                .dateFrom(dateFrom)
                .status("Отсутсвует")
                .build();
    }

    @Transactional
    protected void saveDailyOpenClose(List<DailyOpenClose> dailyOpenCloseList){
        for (DailyOpenClose dailyOpenClose : dailyOpenCloseList){
            if(dailyOpenClose != null) {
                dailyOpenCloseRepository.save(dailyOpenClose);
            }
        }
    }
}