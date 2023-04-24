package com.example.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/h2-console/**");

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable();

         return http
                .authorizeRequests()


                                    //구획별 설정
                //------------------------------------------
                        // 회원 관리 처리 API 전부를 login 없이 허용
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/new").permitAll()
                        .requestMatchers("/new/postdetail/**").permitAll()
                        .requestMatchers("/new/signup").permitAll()
                        .requestMatchers("/new/login").permitAll()
                        .requestMatchers("/postdetail/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/signup/**").permitAll()
                        .requestMatchers("/api/postlist/**").permitAll()
                        .requestMatchers("/api/blog/detail/**").permitAll()
                        //그 외 나머지는 로그인으로 인증을 받아야 이용가능
                        .anyRequest().authenticated()
                //------------------------------------------

                        .and()

                //------------------------------------------
                        //로그인 설정
                        .formLogin()
                        .loginPage("/new/login")
                        .loginProcessingUrl("/new/login")
                        .defaultSuccessUrl("/new")
                        .failureUrl("/user/login?error")
                        .permitAll()
                //------------------------------------------

                        .and()

                //------------------------------------------
                        .logout()
                        // 로그아웃 요청 처리 URL
                        .logoutUrl("/user/logout")
                        .permitAll()
                //------------------------------------------

                        .and()

                //------------------------------------------
                        //예외처리 구성을 설정하는 메소드
                        .exceptionHandling()
                        // "접근 불가" 페이지 URL 설정
                        .accessDeniedPage("/forbidden.html")

                 .and()
                 .build();




    }






}
