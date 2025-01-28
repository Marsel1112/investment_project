package org.invest.repository;

import org.invest.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TickerRepository extends JpaRepository<Ticker, Long> {

    Ticker findByTicker(String ticker);

}
