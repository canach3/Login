package boardProject.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.session.jdbc.JdbcIndexedSessionRepository;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableJdbcHttpSession // Spring Session JDBC 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // 기본 로그인폼 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // 🔑 SessionCreationPolicy 옵션들
                //
                //ALWAYS
                //
                //매 요청마다 항상 새로운 세션 생성.
                //
                //인증이 필요 없더라도 무조건 HttpSession을 만듦.
                //
                //NEVER
                //
                //스프링 시큐리티가 세션을 직접 만들지 않음.
                //
                //대신, 이미 세션이 존재한다면 그 세션을 사용함.
                //
                //세션을 아예 쓰지 않고 토큰 기반(JWT) 인증할 때 자주 사용.
                //
                //IF_REQUIRED (기본값)
                //
                //인증이 필요하면 세션을 만들고, 필요하지 않으면 만들지 않음.
                //
                //즉, 로그인 요청에서만 세션이 생성됨.
                //
                //세션 기반 로그인 방식에서 가장 흔히 사용하는 설정.
                //
                //STATELESS
                //
                //절대 세션을 생성하지 않음, 존재해도 사용하지 않음.
                //
                //JWT 기반 무상태 인증에 사용.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members/signup", "/api/auth/login").permitAll() // 로그인을 하지 않아도 되는 경로
                        .anyRequest().authenticated() // 나머지 경로는 로그인 성공시 허용
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityContext를 세션에 자동 저장하도록 하는 필터
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}