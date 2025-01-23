package org.invest.repository;

import org.invest.entity.UsersDailyOpenCloseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface UsersDailyOpenCloseHistoryRepository extends JpaRepository<UsersDailyOpenCloseHistory, Long> {
    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenClose_SymbolAndDailyOpenClose_DateFromBetween(Long userId,
                                                                                                        String symbol,
                                                                                                        LocalDate from,
                                                                                                        LocalDate to);
    List<UsersDailyOpenCloseHistory> findByUserIdAndDailyOpenClose_Symbol(Long userId, String symbol);
}
