package com.example.security.domain.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
@Entity
@Setter
@Getter
@Table(name = "users")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Column
    private String confirmationToken;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStaus userStaus=UserStaus.USER;

    @NotNull
    @CreationTimestamp
    @Column(name = "register_time", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime register_time;

    @PrePersist
    public void localDate(){
        this.register_time=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }


    @Column
    private String token;

    @Override
    public @NullMarked Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userStaus==UserStaus.ADMIN){
            return List.of(new SimpleGrantedAuthority("Role_User"), new SimpleGrantedAuthority("Role_Admin"));
        }
        return List.of(new SimpleGrantedAuthority("Role_User"));
    }


    @Override
    public  @NullMarked String getUsername() {
        return email;
    }

    @Override
    public @NullMarked boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public @NullMarked boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public @NullMarked boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public @NullMarked boolean isEnabled() {
        return enabled;
    }
}
