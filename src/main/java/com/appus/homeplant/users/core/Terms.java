package com.appus.homeplant.users.core;

import com.appus.homeplant.commons.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TermsType termsType;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Boolean required;

    @Builder
    public Terms(TermsType termsType, String title, String content, Boolean required) {
        this.termsType = termsType;
        this.title = title;
        this.content = content;
        this.required = required;
    }

    public enum TermsType {
        SIGN_UP
    }
}
