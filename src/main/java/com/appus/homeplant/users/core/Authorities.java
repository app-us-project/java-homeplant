package com.appus.homeplant.users.core;

import com.appus.homeplant.commons.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authorities extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    public String getAuthority() {
        return role.name();
    }

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }
}
