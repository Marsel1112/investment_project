package org.invest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "daily_open_close")
public class DailyOpenClose implements Comparable<DailyOpenClose> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    private String symbol;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private String volume;

    private BigDecimal afterHours;

    private BigDecimal preMarket;

    @OneToMany(mappedBy = "dailyOpenClose",fetch = FetchType.LAZY)
    private Set<UsersDailyOpenCloseHistory> userHistories;

    @Override
    public int compareTo(DailyOpenClose o) {
        return this.dateFrom.compareTo(o.dateFrom);
    }
}
