package com.appus.homeplant.users.repository;

import com.appus.homeplant.users.core.Terms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
class TermsRepositoryTest {

    @Autowired
    private TermsRepository termsRepository;

    @BeforeEach
    void init() {
        List<Terms> terms = Arrays.asList(
                Terms.builder()
                        .termsType(Terms.TermsType.SIGN_UP)
                        .title("a")
                        .content("A")
                        .required(true)
                        .build(),

                Terms.builder()
                        .termsType(Terms.TermsType.SIGN_UP)
                        .title("b")
                        .content("b")
                        .required(true)
                        .build(),

                Terms.builder()
                        .termsType(Terms.TermsType.SIGN_UP)
                        .title("c")
                        .content("C")
                        .required(false)
                        .build(),

                Terms.builder()
                        .termsType(Terms.TermsType.SIGN_UP)
                        .title("d")
                        .content("D")
                        .required(false)
                        .build()
        );

        termsRepository.saveAll(terms);
    }

    @DisplayName("회원가입 약정 조회 테스트")
    @Test
    void findByRequiredTrueTest() {
        List<Terms> requiredTrueTerms = termsRepository.findAllByTermsType(Terms.TermsType.SIGN_UP);

        assertEquals(4, requiredTrueTerms.size());
    }
}