package com.appus.homeplant.config;

import com.appus.homeplant.security.JwtTokenFilter;
import com.appus.homeplant.security.JwtTokenProvider;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder를 Bean으로 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //authenticationManager를 Bean으로 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()    //기본 설정 해제(rest api만을 고려함)
                .csrf().disable()   //csrf 보안 토큰 diable 처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 토큰 기반 인증이므로 세션을 사용하지 않습니다.
                .and()
                .authorizeRequests()    //요청에 대한 사용 권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()    //그 외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);    //JwtTokenFilter를 UsernamePasswordAuthenticationFilter 전에 넣습니다.
    }
}
