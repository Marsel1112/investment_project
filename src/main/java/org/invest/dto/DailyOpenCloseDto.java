package org.invest.dto;

import lombok.*;
import org.checkerframework.checker.units.qual.N;
import org.invest.entity.DailyOpenClose;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyOpenCloseDto implements Comparable<DailyOpenCloseDto> {

    private String status;

    private LocalDate from;

    private String symbol;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private String volume;

    private BigDecimal afterHours;

    private BigDecimal preMarket;

    @Override
    public int compareTo(DailyOpenCloseDto o) {
        return this.from.compareTo(o.from);
    }

}
