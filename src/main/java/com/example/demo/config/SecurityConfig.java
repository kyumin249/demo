package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. 세션 및 보안 헤더 설정 (기존 설정)
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block"); // XSS-Protection 헤더설정
                })
            )
            .csrf(withDefaults()) // CSRF 설정
            .sessionManagement(session -> session
                .invalidSessionUrl("/session-expired") // 세션만료시이동페이지
                .maximumSessions(1) // 사용자별세션최대수
                .maxSessionsPreventsLogin(true) // 동시세션제한
            )

            // 2. 경로별 접근 권한 설정 (로그인 기능을 위한 핵심 추가 사항)
            .authorizeHttpRequests(auth -> auth
                // "/join_new"나 정적 리소스 등은 누구나 접근 가능하도록 설정
                .requestMatchers("/join_new", "/css/**", "/js/**", "/images/**").permitAll() 
                .requestMatchers("/", "/api/**").permitAll() // 메인 페이지나 API 일부도 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자만 접근 허용
            )

            // 3. 로그인 폼 설정
            .formLogin(form -> form
                .loginPage("/login") // 로그인 페이지 경로
                .loginProcessingUrl("/api/login_check") // 폼 액션 경로 (인증 처리 URL)
                .defaultSuccessUrl("/board_list", true) // 성공 시 이동 경로
                .failureUrl("/login?error") // 실패 시 이동 경로
                .usernameParameter("username") // 아이디 파라미터 이름
                .passwordParameter("password") // 비밀번호 파라미터 이름
                .permitAll()
            )

            // 4. 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 처리 URL
                .logoutSuccessUrl("/login?logout") // 로그아웃 성공 시 이동 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제
                .permitAll()
            );
            
        return http.build(); // 최종 필터 체인 빌드 및 반환
    }

    @Bean // 암호화 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘 사용
    }
}