package com.appus.homeplant.users.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserAuthorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "authorities_id", nullable = false)
    private Authorities authorities;

    @Builder
    public UserAuthorities(Authorities authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getAuthority() {
        return authorities.getAuthority();
    }

    public void registerUsers(Users users) {
        this.users = users;
    }
}
