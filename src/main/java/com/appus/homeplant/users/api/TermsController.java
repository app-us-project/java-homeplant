package com.appus.homeplant.users.api;

import com.appus.homeplant.users.core.Terms;
import com.appus.homeplant.users.service.TermsService;
import com.appus.homeplant.users.service.dto.TermsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController {

    private final TermsService termsService;

    @GetMapping("/sign-up")
    public ResponseEntity<List<TermsDto>> getServiceTerms(
            @RequestParam(value = "terms-type") Terms.TermsType termsType) {
        List<TermsDto> termsByTermsType = termsService.findTermsByTermsType(termsType);
        return new ResponseEntity<>(termsByTermsType, HttpStatus.OK);
    }
}
