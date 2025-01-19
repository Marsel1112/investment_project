package org.invest.repository;

import org.invest.entity.UsersDailyOpenCloseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UsersDailyOpenCloseHistoryRepository extends JpaRepository<UsersDailyOpenCloseHistory, Long> {
    List<UsersDailyOpenCloseHistory> getUsersDailyOpenCloseHistoriesByUserIdAndSymbolAndDateFromBetween(Long userId,
                                                                                                        String symbol,
                                                                                                        LocalDate from,
                                                                                                        LocalDate to);
    List<UsersDailyOpenCloseHistory> getUsersDailyOpenCloseHistoriesByUserIdAndSymbol(Long userId, String symbol);

}
