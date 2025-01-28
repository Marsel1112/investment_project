package org.invest.repository;

import org.invest.dto.HistoryDto;
import org.invest.entity.UsersDailyOpenCloseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UsersDailyOpenCloseHistoryRepository extends JpaRepository<UsersDailyOpenCloseHistory, Long> {

    default List<UsersDailyOpenCloseHistory> getHistoryByFullHistoryDto(HistoryDto historyDto){
        return findByUserIdAndDailyOpenClose_SymbolAndDailyOpenClose_DateFromBetween(historyDto.getUserId(),
                                                                                     historyDto.getSymbol(),
                                                                                     historyDto.getFrom(),
                                                                                     historyDto.getTo());
    }

    default boolean isHistoryEmpty(HistoryDto historyDto){
        return existsByUserIdAndDailyOpenClose_SymbolAndDailyOpenClose_DateFrom(historyDto.getUserId(),
                                                                                historyDto.getSymbol(),
                                                                                historyDto.getFrom());
    }

    default List<UsersDailyOpenCloseHistory> getHistory(HistoryDto historyDto){
        return findByUserIdAndDailyOpenClose_Symbol(historyDto.getUserId(),
                                                    historyDto.getSymbol());
    }


    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenClose_SymbolAndDailyOpenClose_DateFromBetween(Long userId,
                                                                                                        String symbol,
                                                                                                        LocalDate from,
                                                                                                        LocalDate to);

    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenClose_Symbol(Long userId, String symbol);


    boolean existsByUserIdAndDailyOpenClose_SymbolAndDailyOpenClose_DateFrom(Long userId, String symbol, LocalDate dateFrom);
}
