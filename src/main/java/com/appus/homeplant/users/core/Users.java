package com.appus.homeplant.users.core;

import com.appus.homeplant.commons.audit.BaseEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
public class Users extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @OneToMany(
            mappedBy = "users",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<UserAuthorities> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userStatus.equals(UserStatus.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus.equals(UserStatus.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userStatus.equals(UserStatus.EXPIRED);
    }

    @Override
    public boolean isEnabled() {
        return userStatus.equals(UserStatus.ENABLED);
    }

    public enum UserStatus {
        ENABLED, EXPIRED, LOCKED, WITHDRAWN
    }
}
