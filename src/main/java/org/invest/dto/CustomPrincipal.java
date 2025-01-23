package org.invest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomPrincipal {
    private String email;
    private Long userId;

}
