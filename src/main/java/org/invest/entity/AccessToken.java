package org.invest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@Table(name = "access_token")
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @ManyToOne()
    @JoinColumn(name = "refresh_token_id", nullable = false)
    private RefreshToken refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private String value;

}