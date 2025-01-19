package org.invest.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id", nullable = false)
    private RefreshToken refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private String value;

}