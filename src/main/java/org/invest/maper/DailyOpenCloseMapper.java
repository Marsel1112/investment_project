package org.invest.maper;

import org.invest.dto.DailyOpenCloseDto;
import org.invest.entity.DailyOpenClose;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyOpenCloseMapper {

     @Mapping(target = "status", source = "status")
     @Mapping(target = "from", source = "dateFrom")
     @Mapping(target = "symbol", source = "symbol")
     @Mapping(target = "open", source = "open")
     @Mapping(target = "high", source = "high")
     @Mapping(target = "low", source = "low")
     @Mapping(target = "close", source = "close")
     @Mapping(target = "volume", source = "volume")
     @Mapping(target = "afterHours", source = "afterHours")
     @Mapping(target = "preMarket", source = "preMarket")
    DailyOpenCloseDto toDto(DailyOpenClose entity);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "dateFrom", source = "from")
    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "open", source = "open")
    @Mapping(target = "high", source = "high")
    @Mapping(target = "low", source = "low")
    @Mapping(target = "close", source = "close")
    @Mapping(target = "volume", source = "volume")
    @Mapping(target = "afterHours", source = "afterHours")
    @Mapping(target = "preMarket", source = "preMarket")
    DailyOpenClose toEntity(DailyOpenCloseDto dto);

    List<DailyOpenCloseDto> toDtoList(List<DailyOpenClose> entities);

    List<DailyOpenClose> toEntityList(List<DailyOpenCloseDto> dtoList);
}
