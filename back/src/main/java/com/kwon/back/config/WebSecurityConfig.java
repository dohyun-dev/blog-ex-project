package com.kwon.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwon.back.dto.ErrorResponse;
import com.kwon.back.exception.CustomException;
import com.kwon.back.exception.ErrorCode;
import com.kwon.back.filter.JwtAuthenticationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .cors(cors -> cors.disable()) // CORS 비활성화
                .httpBasic(httpBasic -> httpBasic.disable()) // 기본 로그인 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 경로 허용

                                // 루트 경로(`/`), 인증 관련 경로(`/api/v1/auth/**`), 검색 API(`/api/v1/search`), 파일 다운로드 경로(`/file/**`)에 대한 모든 요청을 허용합니다.
                                // 여기서 `**`는 해당 경로 아래의 모든 하위 경로를 의미하고, `*`는 한 단계의 하위 경로만을 의미합니다.
                                .requestMatchers(
                                        "/",
                                        "/api/v1/auth/**",
                                        "/api/v1/search",
                                        "/file/**"
                                ).permitAll()

                                // GET 요청에 대해서만, 게시판(`/api/v1/boards/**`)과 사용자 프로필(`/api/v1/users/*`) 조회 관련 경로에 대한 접근을 허용합니다.
                                // 이는 정보 조회 기능에 대한 일반적인 접근을 허용하면서, 생성, 수정, 삭제 등 다른 작업에 대해서는 보다 엄격한 접근 제어를 가능하게 합니다.
                                .requestMatchers(
                                        HttpMethod.GET,
                                        "/api/v1/boards/**",
                                        "/api/v1/users/*"
                                ).permitAll()

                                // 그 외 모든 요청에 대해서는 인증된 사용자만 접근할 수 있도록 합니다.
                                // 이는 기본적으로 애플리케이션의 나머지 부분이 보호되어야 함을 의미하며, 인증되지 않은 사용자는 접근할 수 없습니다.
                                .anyRequest().authenticated()
                ) // 모든 요청에 대해 인증 요구
                .headers(headers -> headers.frameOptions().disable()); // H2 콘솔을 위한 X-Frame-Options 비활성화

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // PasswordEncoder 빈 정의 (비밀번호 암호화에 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // HTTP 상태 코드를 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ResponseEntity<ErrorResponse> errorResponse = ErrorResponse.toResponseEntity(CustomException.of(ErrorCode.AUTHENTICATION_REQUIRED));

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse.getBody()));
    }
}
