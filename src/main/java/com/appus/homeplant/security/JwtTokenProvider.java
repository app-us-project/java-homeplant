package com.appus.homeplant.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger= LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret")
    private String jwtSecret;   //암호화 키

    @Value("${app.jwtExpirationInMs")
    private int jwtExpirationInMs;  //만료일 상수

    //jwt 생성
    public String generateToken(Authentication authentication) {
        //접근 주체: UserDetail에서 추가사항을 넣기 위해 만듦
        AccountPrincipal accountPrincipal=(AccountPrincipal)authentication.getPrincipal();

        Date now=new Date();
        Date expiryDate=new Date(now.getTime()+jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(accountPrincipal.getId()))    //PAYLOAD에 들어갈 sub
                .setIssuedAt(new Date())    //생성일
                .setExpiration(expiryDate)  //만료일
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  //암호화 방식
                .compact(); //토큰 생성 메소드
    }

    //JWT로부터 UserId 획득
    public Long getUserIdFromJWT(String token) {
        Claims claims= Jwts
                .parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
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
