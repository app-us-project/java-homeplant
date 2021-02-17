package com.appus.homeplant.users.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TermsDto {

    private Long id;
    private String title;
    private String content;
    private Boolean required;

    @Builder
    public TermsDto(Long id, String title, String content, Boolean required) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.required = required;
    }
}
