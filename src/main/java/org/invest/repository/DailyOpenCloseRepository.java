package org.invest.repository;

import org.invest.entity.DailyOpenClose;
import org.invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyOpenCloseRepository extends JpaRepository<DailyOpenClose, Long> {
    DailyOpenClose findBySymbolAndDateFrom(String symbol, LocalDate dateFrom);


}
