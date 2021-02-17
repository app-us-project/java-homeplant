package com.appus.homeplant.commons.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Property values injection test")
    void propertyValuesTest() {
        Environment environment = applicationContext.getEnvironment();

        String secretKey = environment.getProperty("app.jwt-secret");
        Long jwtExpirationInMs = environment.getProperty("app.jwt-expiration-in-ms", Long.class);

        assertNotNull(secretKey);
        assertNotNull(jwtExpirationInMs);
    }

    @Test
    @DisplayName("Creates token test")
    void createTokenTest() {
        //given
        String testId = "1";
        List<String> testRoles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        //when
        String token = jwtTokenProvider.generateToken(testId, testRoles);

        //then
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    @DisplayName("Resolves token test")
    void resolveTokenTest() {
        //given
        final String testId = "1";
        final String userRole = "ROLE_USER";
        final String adminRole = "ROLE_ADMIN";
        List<String> testRoles = Arrays.asList(userRole, adminRole);
        String givenToken = jwtTokenProvider.generateToken(testId, testRoles);

        //when
        JwtToken jwtToken = jwtTokenProvider.resolveToken(givenToken);

        //then
        assertEquals("1", jwtToken.getId());
        assertTrue(
                jwtToken.getAuthorities().stream()
                        .anyMatch(role -> role.equals(userRole))
        );
        assertTrue(
                jwtToken.getAuthorities().stream()
                        .anyMatch(role -> role.equals(adminRole))
        );
    }
}