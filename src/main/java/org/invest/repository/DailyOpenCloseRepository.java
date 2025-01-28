package org.invest.repository;

import org.invest.entity.DailyOpenClose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DailyOpenCloseRepository extends JpaRepository<DailyOpenClose, Long> {
    DailyOpenClose findBySymbolAndDateFrom(String symbol, LocalDate dateFrom);
    boolean existsBySymbolAndDateFrom(String symbol, LocalDate dateFrom);
}
