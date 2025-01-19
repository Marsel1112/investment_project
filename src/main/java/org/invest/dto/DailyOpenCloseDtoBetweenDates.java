package org.invest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyOpenCloseDtoBetweenDates {
    private String ticker;
    private LocalDate openDate;
    private LocalDate closeDate;
}
