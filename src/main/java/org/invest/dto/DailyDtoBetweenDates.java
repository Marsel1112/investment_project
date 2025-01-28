package org.invest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyDtoBetweenDates {
    @Size(max = 50, message = "Длина тикера должна быть до 50 символов")
    @NotBlank(message = "Тикер акции не должен быть пустым")
    private String ticker;

    private LocalDate openDate;

    private int countDay;
}
