package com.appus.homeplant.commons.jwt.filter;

import com.appus.homeplant.commons.jwt.JwtToken;
import com.appus.homeplant.commons.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String rawJwtToken = getJwtFromRequest(request);
            JwtToken jwtToken = resolveToken(rawJwtToken);

            if (!Objects.isNull(jwtToken)) {
                List<GrantedAuthority> authorities = jwtToken.getAuthorities().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(toList());

                SecurityContextHolder.getContext()
                        .setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        jwtToken.getId(),
                                        null,
                                        authorities
                                )
                        );
            }

        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private JwtToken resolveToken(String rawJwtToken) {
        if (StringUtils.hasText(rawJwtToken)) {
            return tokenProvider.resolveToken(rawJwtToken);
        }

        return null;
    }
}
