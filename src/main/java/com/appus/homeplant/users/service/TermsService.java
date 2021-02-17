package com.appus.homeplant.users.service;

import com.appus.homeplant.users.core.Terms;
import com.appus.homeplant.users.repository.TermsRepository;
import com.appus.homeplant.users.service.dto.TermsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;

    @Transactional(readOnly = true)
    public List<TermsDto> findTermsByTermsType(Terms.TermsType termsType) {
        List<Terms> terms = termsRepository.findAllByTermsType(termsType);

        return terms.stream()
                .map(t -> TermsDto.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .content(t.getContent())
                        .required(t.getRequired())
                        .build()
                ).collect(toList());
    }
}
