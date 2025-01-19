package org.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "daily_open_close")
public class DailyOpenClose implements Comparable<DailyOpenClose> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

    @Column(name = "date_from")
    private LocalDate from;

    private String symbol;

    private BigDecimal open;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal close;

    private String volume;

    private BigDecimal afterHours;

    private BigDecimal preMarket;

    @OneToMany(mappedBy = "dailyOpenClose",fetch = FetchType.LAZY)
    private Set<UsersDailyOpenCloseHistory> users;

    @Override
    public int compareTo(DailyOpenClose o) {
        return this.from.compareTo(o.from);
    }
}
