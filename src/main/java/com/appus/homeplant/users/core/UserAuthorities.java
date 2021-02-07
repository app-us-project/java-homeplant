package com.appus.homeplant.users.core;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Entity
public class UserAuthorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorities_id")
    private Authorities authorities;

    @Override
    public String getAuthority() {
        return authorities.getAuthority();
    }
}
