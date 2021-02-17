package com.appus.homeplant.commons.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;   //암호화 키

    @Value("${app.jwt-expiration-in-ms}")
    private Long jwtExpirationInMs;  //만료일 상수

    private static final String HOME_PLANT = "HomePlantToken";
    private static final String ASIA_SEOUL = "Asia/Seoul";

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    //jwt 생성
    public String generateToken(String id, List<String> roles) {
        Claims claims = Jwts.claims()
                .setSubject(HOME_PLANT)
                .setId(id);
        claims.put("authorities", roles);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())    //생성일
                .setExpiration(expiryDate)  //만료일
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  //암호화 방식
                .compact(); //토큰 생성 메소드
    }

    public JwtToken resolveToken(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        return JwtToken.builder()
                .subject(HOME_PLANT)
                .id(claims.getId())
                .issuedAt(
                        claims.getIssuedAt()
                                .toInstant()
                                .atZone(ZoneId.of(ASIA_SEOUL))
                                .toLocalDateTime()
                )
                .expiredAt(
                        claims.getExpiration()
                                .toInstant()
                                .atZone(ZoneId.of(ASIA_SEOUL))
                                .toLocalDateTime()
                )
                .authorities(authorities)
                .build();
    }

    //JWT로부터 UserId 획득
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getId());
    }

    //JWT 유효성 검사
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT class string is empty");
        }
        return false;
    }
}
