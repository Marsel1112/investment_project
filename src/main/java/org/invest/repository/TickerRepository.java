package org.invest.repository;

import org.invest.entity.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TickerRepository extends JpaRepository<Ticker, Long> {

    default boolean isTableEmpty(){
        return count()==0;
    };

    Ticker findByTicker(String ticker);

}
