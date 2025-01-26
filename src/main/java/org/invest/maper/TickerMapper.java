package org.invest.maper;

import org.invest.dto.TickerDto;
import org.invest.entity.Ticker;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TickerMapper {

    TickerDto toTickerDto(Ticker ticker);
    Ticker toTicker(TickerDto tickerDto);

    List<TickerDto> toTickerDtoList(List<Ticker> tickers);
    List<Ticker> toTickerList(List<TickerDto> tickerDtoList);
}
