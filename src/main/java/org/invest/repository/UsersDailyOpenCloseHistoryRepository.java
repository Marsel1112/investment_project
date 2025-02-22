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
        return findByUserIdAndDailyOpenCloseSymbolAndDailyOpenCloseDateFromBetween(historyDto.getUserId(),
                                                                                     historyDto.getSymbol(),
                                                                                     historyDto.getFrom(),
                                                                                     historyDto.getTo());
    }

    default boolean isHistoryEmpty(HistoryDto historyDto){
        return existsByUserIdAndDailyOpenCloseSymbolAndDailyOpenCloseDateFrom(historyDto.getUserId(),
                                                                                historyDto.getSymbol(),
                                                                                historyDto.getFrom());
    }

    default List<UsersDailyOpenCloseHistory> getHistory(HistoryDto historyDto){
        return findByUserIdAndDailyOpenCloseSymbol(historyDto.getUserId(),
                                                    historyDto.getSymbol());
    }


    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenCloseSymbolAndDailyOpenCloseDateFromBetween(Long userId,
                                                                                                        String symbol,
                                                                                                        LocalDate from,
                                                                                                        LocalDate to);

    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenCloseSymbol(Long userId, String symbol);


    boolean existsByUserIdAndDailyOpenCloseSymbolAndDailyOpenCloseDateFrom(Long userId, String symbol, LocalDate dateFrom);
}
