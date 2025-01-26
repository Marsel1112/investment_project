package org.invest.dto;

import lombok.Data;

@Data
public class TickerDto {
    private String ticker;
    private String name;
    private String market;
    private String locale;
    private String primaryExchange;
    private String type;
    private boolean active;
    private String currencyName;
    private String cik;
    private String compositeFigi;
    private String shareClassFigi;
    private String lastUpdatedUtc;
}
