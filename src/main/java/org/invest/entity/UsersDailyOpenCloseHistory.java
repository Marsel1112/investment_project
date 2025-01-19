package org.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_daily_open_close_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDailyOpenCloseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_created",
            updatable = false,
            insertable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_open_close_id", nullable = false)
    private DailyOpenClose dailyOpenClose;
}
