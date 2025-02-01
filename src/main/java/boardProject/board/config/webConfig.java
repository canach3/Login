package boardProject.board.config;

import boardProject.board.auth.service.SessionManager;
import boardProject.board.config.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class webConfig implements WebMvcConfigurer {

    private final SessionManager sessionManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(sessionManager))
                .order(1)
                .addPathPatterns("/**")
                // 정적 리소스 및 특정 경로 제외
                .excludePathPatterns("/", "/member/save", "/auth/login", "/auth/logout", "/auth/styles.css", "/styles/**", "/style.css", "/js/**", "/images/**", "/error");
    }
}
