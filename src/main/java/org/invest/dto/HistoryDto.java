package org.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private Long userId;
    private String symbol;
    private LocalDate from;
    private LocalDate to;
}
