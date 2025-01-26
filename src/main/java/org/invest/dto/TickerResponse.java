package org.invest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TickerResponse {
    private TickerDto results;
}
