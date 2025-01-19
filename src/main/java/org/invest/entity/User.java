package org.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    private String email;

    private String password;

    private String name;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<UsersDailyOpenCloseHistory> dailyOpenCloseHistoryList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
