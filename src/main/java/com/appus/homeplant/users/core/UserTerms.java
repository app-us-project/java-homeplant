package com.appus.homeplant.users.core;

import com.appus.homeplant.commons.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserTerms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terms_id", nullable = false)
    private Terms terms;

    private Boolean agreement;

    public UserTerms(Users users) {
        this.users = users;
    }

    public void agreeTerms(Terms terms, Boolean agreement) {
        if (terms.getRequired() && !agreement) {
            throw new IllegalArgumentException("필수 항목이 동의되지 않았습니다.");
        }
        this.terms = terms;
        this.agreement = agreement;
    }
}
