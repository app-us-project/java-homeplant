package com.appus.homeplant.users.core;

import com.appus.homeplant.commons.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<UserAuthorities> authorities = Collections.emptyList();

    @Builder
    public Users(String email, String password, String phoneNumber, UserStatus userStatus, List<UserAuthorities> authorities) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.userStatus = userStatus;
        this.authorities = authorities;
    }

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

    public void addAuthorities(List<UserAuthorities> userAuthorities) {
        this.authorities.addAll(userAuthorities);
        for (UserAuthorities userAuthority : userAuthorities) {
            userAuthority.registerUsers(this);
        }
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public boolean isSamePhoneNumber(String phoneNumber) {
        return this.phoneNumber.equals(phoneNumber);
    }

    public enum UserStatus {
        ENABLED, EXPIRED, LOCKED, WITHDRAWN
    }
}
